package com.ecomerce_app.product_service.repository;

import com.ecomerce_app.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}
