package com.promptwars.debugstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    void searchShouldFindProductByName() {
        repository.save(new Product("Gaming Mouse", 1500, 5));

        List<Product> result = repository.findByNameContainingIgnoreCase("mouse");

        assertFalse(result.isEmpty());
    }
}