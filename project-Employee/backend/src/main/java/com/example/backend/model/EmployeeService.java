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
    } // ruft RePo um all Mit Abzu rufen
    public Employee findById(String id){
        return employeeRepository.findById(id).orElseThrow();
    } //Op <E> ob aufr
    public Employee saveEmployee(Employee employee, MultipartFile image) throws IOException {
        String newId = idService.createId();//neu Id
        Employee newEmployee = new Employee(
                newId,
                employee.firstName(),
                employee.lastName(),
                employee.email(),
                employee.url()
        );
        if(image != null){ // WEnn Nicht nUll ist
            String url = cloudinaryService.uploadImage(image);
            newEmployee = newEmployee.withUrl(url); //rasmakanman bdare image übergeben
        }
        return employeeRepository.save(newEmployee); // gespeicherte employee obj
    }
   public Employee update(Employee employee){

       return employeeRepository.save(employee); // Aktu Emplo obj zu speichern
    }
    public void deleteEmployee(String id){

        employeeRepository.deleteById(id);
    }
}









