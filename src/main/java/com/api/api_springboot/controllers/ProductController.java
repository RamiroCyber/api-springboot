package com.api.api_springboot.controllers;
import com.api.api_springboot.dtos.ProductRecordDto;
import com.api.api_springboot.models.ProductModel;
import com.api.api_springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping()
    public ResponseEntity<ProductModel> insertProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping()
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> listProducts = productRepository.findAll();
        if(!listProducts.isEmpty()) {
            for (ProductModel product: listProducts) {
                UUID productId = product.getId();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(productId)).withSelfRel());
            }
        }
        return ResponseEntity.ok().body(listProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id")UUID id) {
        Optional<ProductModel> product = productRepository.findById(id);
        if(product.isPresent()) {
            product.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
            return ResponseEntity.ok().body(product.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> product = productRepository.findById(id);
        if(product.isPresent()) {
            var productModel = product.get();
            BeanUtils.copyProperties(productRecordDto,productModel);
            return ResponseEntity.ok().body(productRepository.save(productModel));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> product = productRepository.findById(id);
        if(product.isPresent()) {
            productRepository.delete(product.get());
            return ResponseEntity.ok().body("Product deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }
}
