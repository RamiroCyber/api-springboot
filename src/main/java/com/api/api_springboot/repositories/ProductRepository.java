package com.api.api_springboot.repositories;
import com.api.api_springboot.models.product.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
}
