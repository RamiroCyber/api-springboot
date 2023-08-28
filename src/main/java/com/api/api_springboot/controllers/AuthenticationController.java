package com.api.api_springboot.controllers;
import com.api.api_springboot.dtos.AuthenticationRecordDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody  @Valid AuthenticationRecordDto authentication) {
   var userPassword = new UsernamePasswordAuthenticationToken(authentication.email(), authentication.password());
    }
}