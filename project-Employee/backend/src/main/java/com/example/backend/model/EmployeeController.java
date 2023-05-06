package com.example.backend.model;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {

        return ResponseEntity.ok(employeeService.findAll());
    }


    @GetMapping("employees/{id}")

    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {

        return ResponseEntity.ok(employeeService.findById(id));
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> postEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable String id) {
        if (employeeRepository.existsById(id)) {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } else return ResponseEntity.notFound().build();
    }
}