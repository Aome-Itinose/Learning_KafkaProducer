package org.aome.learningkafka.controllers;

import lombok.RequiredArgsConstructor;
import org.aome.learningkafka.services.ProductService;
import org.aome.learningkafka.services.dto.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody ProductDto productDto) {
        return productService.createAndReturnId(productDto);
    }
}
