package com.example.backend.model;

import com.example.backend.service.IdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService{
    private final EmployeeRepository employeeRepository;
    private final IdService idService;



    public List<Employee> findAll(){

        return employeeRepository.findAll();
    }


    public Employee findById(String id){

        return employeeRepository.findById(id).orElseThrow();
    }


    public Employee saveEmployee(Employee employee) {
        String newId = idService.createId();
        Employee newEmployee = new Employee(
                newId,
                employee.firstName(),
                employee.lastName(),
                employee.email()

        );
        return employeeRepository.save(newEmployee);
    }


    public void deleteEmployee(String id){

        employeeRepository.deleteById(id);
    }
}









