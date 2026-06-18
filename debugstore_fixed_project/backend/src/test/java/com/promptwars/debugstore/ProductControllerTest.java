package com.promptwars.debugstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ProductController.class, HealthController.class, GlobalExceptionHandler.class})
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void healthApiShouldReturnBackendRunning() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Backend is running"));
    }

    @Test
    void productsApiShouldReturnOk() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(
                new Product("Laptop", 55000, 5)
        ));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk());
    }

    @Test
    void addProductShouldWork() throws Exception {
        Product savedProduct = new Product("Monitor", 9000, 4);
        savedProduct.setId(1L);

        when(productService.addProduct(any(Product.class))).thenReturn(savedProduct);

        String json = "{\"name\":\"Monitor\",\"price\":9000,\"quantity\":4}";

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }
}