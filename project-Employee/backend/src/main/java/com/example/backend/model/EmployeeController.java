package com.example.backend.model;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/api/")
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
    public ResponseEntity<Employee> postEmployee(@RequestPart("data")  Employee employee, @RequestPart(name="file",required = false) MultipartFile image) throws IOException {
        return new ResponseEntity<>(employeeService.saveEmployee(employee,image), HttpStatus.CREATED);
    }

   /* @PostMapping("/employees")
    public ResponseEntity<Employee> postEmployee
            (@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.
                saveEmployee(employee), HttpStatus.CREATED);
    }*/




    @PutMapping(path = {"/employees/{id}"})
    Employee update(@PathVariable String id,@RequestBody Employee employee){
        if(!employee.id().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The id in the url does not match the request bodys id");
       }
        return  employeeService.update(employee);
    }



    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable String id) {
        if (employeeRepository.existsById(id)) {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } else return ResponseEntity.notFound().build();
    }
}