package com.example.backend.model;

import com.example.backend.service.IdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
@RequiredArgsConstructor
public class EmployeeService{
    private final EmployeeRepository employeeRepository;
    private final IdService idService;
    private final CloudinaryService cloudinaryService;
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }
    public Employee findById(String id){
        return employeeRepository.findById(id).orElseThrow();
    }
    public Employee saveEmployee(Employee employee, MultipartFile image) throws IOException {
        String newId = idService.createId();
        Employee newEmployee = new Employee(
                newId,
                employee.firstName(),
                employee.lastName(),
                employee.email(),
                employee.url()
        );
        if(image != null){
            String url = cloudinaryService.uploadImage(image);
            newEmployee = newEmployee.withUrl(url);
        }
        return employeeRepository.save(newEmployee);
    }
   public Employee update(Employee employee){

       return employeeRepository.save(employee);
    }
    public void deleteEmployee(String id){

        employeeRepository.deleteById(id);
    }
}









