package com.ideal.service;

import com.ideal.dto.EmployeeDto;
import com.ideal.entity.Employee;
import com.ideal.exception.NoChangeException;
import com.ideal.exception.ResourceNotFoundException;
import com.ideal.repository.EmployeeRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
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

    public void saveEmployeesFromExcel(MultipartFile file) throws Exception{
        List<Employee> employees = new ArrayList<>();

        try(InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)){
            Sheet sheet = workbook.getSheetAt(0);
            boolean firstRow = true;

            for(Row row: sheet){
                if(firstRow){
                    firstRow = false;
                    continue;
                }
                Employee employee = new Employee();
                employee.setName(row.getCell(0).getStringCellValue());
                employee.setYoe((int)row.getCell(1).getNumericCellValue());
                employees.add(employee);
            }

            employeeRepository.saveAll(employees);
        }
    }

}
