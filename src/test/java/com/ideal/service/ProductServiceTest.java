package com.ideal.service;

import com.ideal.client.ProductClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductClient productClient;
    private ProductService productService;

    @BeforeEach
    void setup() {
        productClient = Mockito.mock(ProductClient.class);
        productService = new ProductService();
        // inject mock
        org.springframework.test.util.ReflectionTestUtils.setField(productService, "productClient", productClient);
    }

    @Test
    void getAllProducts_shouldReturnProductsFromClient() {
        Map<String, Object> mockResponse = Map.of("product1", "Laptop", "product2", "Phone");
        when(productClient.getProducts()).thenReturn(mockResponse);

        Map<String, Object> result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertEquals("Laptop", result.get("product1"));
        assertEquals("Phone", result.get("product2"));

        verify(productClient, times(1)).getProducts();
    }
}