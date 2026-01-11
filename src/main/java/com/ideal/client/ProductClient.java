package com.ideal.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "productClient", url = "https://dummyjson.com")
public interface ProductClient {

    @GetMapping("/products")
    Map<String, Object> getProducts();

}
