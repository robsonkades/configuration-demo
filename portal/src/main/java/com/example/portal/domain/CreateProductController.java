package com.example.portal.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class CreateProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public void create() {

        var product = new ProductEntity();
        product.setId(UUID.randomUUID().toString());
        product.setName("product 1");

        productRepository.save(product);
    }

    @GetMapping
    public List<ProductEntity> list() {

        var product = new ProductEntity();
        product.setId(UUID.randomUUID().toString());
        product.setName("product 1");

        return productRepository.findAll();
    }
}
