package com.ideal.service;

import com.ideal.dto.EmployeeDto;
import com.ideal.entity.Employee;
import com.ideal.exception.NoChangeException;
import com.ideal.exception.ResourceNotFoundException;
import com.ideal.repository.EmployeeRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @BeforeEach
    void setup() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeService();
        // inject repository
        org.springframework.test.util.ReflectionTestUtils.setField(employeeService, "employeeRepository", employeeRepository);
    }

    @Test
    void createEmployee_shouldReturnSavedEmployee() {
        EmployeeDto dto = new EmployeeDto("John", 5, "admin", "admin", null, null);
        Employee savedEmployee = new Employee();
        savedEmployee.setName("John");
        savedEmployee.setYoe(5);

        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

        Employee result = employeeService.createEmployee(dto);

        assertEquals("John", result.getName());
        assertEquals(5, result.getYoe());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void getEmployee_existingId_shouldReturnEmployee() {
        Employee emp = new Employee();
        emp.setName("Alice");
        when(employeeRepository.findById(1)).thenReturn(Optional.of(emp));

        Employee result = employeeService.getEmployee(1);

        assertEquals("Alice", result.getName());
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void getEmployee_nonExistingId_shouldThrowException() {
        when(employeeRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> employeeService.getEmployee(99));
        assertTrue(ex.getMessage().contains("Employee with ID: 99 not found"));
    }

    @Test
    void getAllEmployees_shouldReturnList() {
        when(employeeRepository.findAll()).thenReturn(List.of(new Employee(), new Employee()));

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void updateEmployee_existingAndChanged_shouldReturnUpdated() {
        Employee emp = new Employee();
        emp.setName("Old");
        emp.setYoe(2);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(emp));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(i -> i.getArgument(0));

        EmployeeDto dto = new EmployeeDto("New", 3, null, "admin", null, null);
        Employee result = employeeService.updateEmployee(1, dto);

        assertEquals("New", result.getName());
        assertEquals(3, result.getYoe());
        assertEquals("admin", result.getUpdatedBy());
    }

    @Test
    void updateEmployee_noChange_shouldThrowNoChangeException() {
        Employee emp = new Employee();
        emp.setName("Same");
        emp.setYoe(5);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(emp));

        EmployeeDto dto = new EmployeeDto("Same", 5, null, "admin", null, null);

        assertThrows(NoChangeException.class, () -> employeeService.updateEmployee(1, dto));
    }

    @Test
    void updateEmployee_nonExisting_shouldThrowResourceNotFound() {
        when(employeeRepository.findById(100)).thenReturn(Optional.empty());
        EmployeeDto dto = new EmployeeDto("Name", 1, null, "admin", null, null);

        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(100, dto));
    }

    @Test
    void saveEmployeesFromExcel_shouldSaveAll() throws Exception {
        // create excel in memory
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Name");
        header.createCell(1).setCellValue("YOE");

        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("Emp1");
        row1.createCell(1).setCellValue(2);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();

        MultipartFile file = new MockMultipartFile("file", "employees.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", bos.toByteArray());

        employeeService.saveEmployeesFromExcel(file);

        ArgumentCaptor<List<Employee>> captor = ArgumentCaptor.forClass(List.class);
        verify(employeeRepository, times(1)).saveAll(captor.capture());
        List<Employee> savedEmployees = captor.getValue();

        assertEquals(1, savedEmployees.size());
        assertEquals("Emp1", savedEmployees.get(0).getName());
        assertEquals(2, savedEmployees.get(0).getYoe());
    }
}