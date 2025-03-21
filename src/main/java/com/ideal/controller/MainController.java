package com.ideal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/main")
public class MainController {

    @GetMapping("/get/{name}")
    public ResponseEntity<String> getMathod(@PathVariable(name = "name") String userName, @RequestParam(required = false, name = "org") String organization, @RequestBody(required = false) Integer countOfLaptops){
        return new ResponseEntity<>("Hi " + userName + " Good Day from " + organization + " You have " + countOfLaptops + " Laptops", HttpStatus.OK);
    }

    @PostMapping("/post/{name}")
    public ResponseEntity<String> postMathod(@PathVariable(name = "name") String userName, @RequestParam(required = false, name = "org") String organization, @RequestBody(required = false) Integer countOfLaptops){
        return new ResponseEntity<>("POST METHOD: Hi " + userName + " Good Day from " + organization + " You have " + countOfLaptops + " Laptops", HttpStatus.OK);
    }

}
