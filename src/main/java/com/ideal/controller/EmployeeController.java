package com.ideal.controller;

import com.ideal.dto.EmployeeDto;
import com.ideal.entity.Employee;
import com.ideal.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.createEmployee(employeeDto), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Integer id){
        return new ResponseEntity<>(employeeService.getEmployee(id), HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestParam Integer id, @RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.updateEmployee(id, employeeDto), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            return new ResponseEntity<>("Please upload a valid Excel file", HttpStatus.BAD_REQUEST);
        }
        try{
            employeeService.saveEmployeesFromExcel(file);
            return new ResponseEntity<>("File uploaded successfully...", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error uploading file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
