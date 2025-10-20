package com.ideal.service;

import com.ideal.dto.EmployeeDto;
import com.ideal.entity.Employee;
import com.ideal.exception.NoChangeException;
import com.ideal.exception.ResourceNotFoundException;
import com.ideal.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(EmployeeDto employeeDto){
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setYoe(employeeDto.getYoe());
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(Integer id){
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee with ID: " + id + " not found!!!"));
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(Integer id, EmployeeDto employeeDto){
        // check no employee found
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with ID: " + id + " not found!!!"));
        // check if no change in updated details
        if(employee.getName().equals(employeeDto.getName()) && employee.getYoe().equals(employeeDto.getYoe())){
            throw new NoChangeException("No change in employee with ID: " + id);
        }
        employee.setName(employeeDto.getName());
        employee.setYoe(employeeDto.getYoe());
        return employeeRepository.save(employee);
    }

}
