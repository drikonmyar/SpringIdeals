package com.ideal.service;

import com.ideal.client.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private ProductClient productClient;

    public Map<String, Object> getAllProducts(){
        return productClient.getProducts();
    }

}
