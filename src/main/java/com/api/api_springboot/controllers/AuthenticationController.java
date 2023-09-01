package com.api.api_springboot.controllers;

import com.api.api_springboot.dtos.AuthenticationRecordDto;
import com.api.api_springboot.dtos.RegisterRecordDto;
import com.api.api_springboot.models.user.UserModel;
import com.api.api_springboot.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationRecordDto authentication) {
        var userPassword = new UsernamePasswordAuthenticationToken(authentication.email(), authentication.password());
        var auth = this.authenticationManager.authenticate(userPassword);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/register")
    ResponseEntity<Object> register(@RequestBody @Valid RegisterRecordDto registerRecordDto) {
        if (this.userRepository.findByEmail(registerRecordDto.email()) != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already registered");

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRecordDto.password());

        var newUser = new UserModel(registerRecordDto.email(), registerRecordDto.userName(), encryptedPassword, registerRecordDto.role());

        this.userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
