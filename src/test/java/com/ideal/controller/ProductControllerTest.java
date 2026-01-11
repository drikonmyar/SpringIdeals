package com.ideal.controller;

import com.ideal.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void getAllProducts_shouldReturnProductsMap() {
        // Arrange
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("productCount", 2);
        mockResponse.put("products", "Sample Products");

        when(productService.getAllProducts()).thenReturn(mockResponse);

        // Act
        ResponseEntity<Map<String, Object>> response = productController.getAllProducts();

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(mockResponse);
    }
}