package com.francislainy.ecomproductservice.controller;

import com.francislainy.ecomproductservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProductService productService;

    @Test
    void shouldCreateProduct() throws Exception {
        String productJson = """
                {
                    "name": "Test Product",
                    "price": 99.99,
                    "description": "Test Description"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .content(productJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(productService, times(1)).createProduct(any());
    }
}
