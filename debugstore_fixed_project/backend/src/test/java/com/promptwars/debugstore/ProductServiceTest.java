package com.promptwars.debugstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void shouldAddProduct() {
        Product product = new Product("Phone", 20000, 3);

        Product saved = productService.addProduct(product);

        assertEquals("Phone", saved.getName());
        assertNotNull(saved.getId());
    }

    @Test
    void shouldRejectNegativePrice() {
        Product product = new Product("Bad Product", -100, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            productService.addProduct(product);
        });
    }
}