package com.example.backend.model;
import com.example.backend.service.IdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
class EmployeeServiceTest {
    private final IdService idService = mock(IdService.class);
    private final EmployeeRepository employeeRepository =
            mock(EmployeeRepository.class);
    private  final CloudinaryService cloudinaryService = mock(CloudinaryService.class);
     EmployeeService employeeService = new EmployeeService(employeeRepository,idService,cloudinaryService);
    private Employee employee;
    MultipartFile image = null;
    @BeforeEach
    void setUp() {

        employee = new Employee("123", "Lani", "lani", "lani@gmail.com","url");
    }
    @Test
    void findAll_shouldReturnEmptyList_whenRepoIsEmpty() {
        //Given
        when(employeeRepository.findAll())
                .thenReturn(Collections.emptyList());
        //When
        List <Employee> actual = employeeService.findAll();
        //Then
        verify(employeeRepository).findAll();
        assertThat(actual).isInstanceOf(List.class).isEmpty();
    }
    @Test
    void findAll_ShouldListWithOneEmployee_WhenRepoOneEmployee() {
        //Given
        when(employeeRepository.findAll())
                .thenReturn(List.of(employee));

        //When
        List<Employee> actual = employeeService.findAll();
        //Then
        verify(employeeRepository).findAll();
        assertThat(actual).isInstanceOf(List.class).contains(employee);
    }

    @Test
    void findById_shouldReturnEmployeeWhenEmployeeExists() {
        when(employeeRepository.findById(employee.id()))
                .thenReturn(Optional.of(employee));
     Employee actual = employeeService.findById(employee.id());
       Employee expected = employee;
        verify(employeeRepository).findById(employee.id());
        assertEquals(expected, actual);
    }
  @Test
    void findById_shouldThrowException_whenIdDoesNotExist() {
            when(employeeRepository.findById("123"))
                    .thenThrow(NoSuchElementException.class);
            try {
                employeeService.findById(employee.id());

            } catch (NoSuchElementException Ignored) {
                verify(employeeRepository).findById("123");
            }
    }
    @Test
    void saveEmployee_ShouldReturnSaveEmployee()  throws IOException {
        when(cloudinaryService.uploadImage(any())).thenReturn("url");
        when(idService.createId())
                .thenReturn(employee.id());
        when(employeeRepository.save(employee))
                .thenReturn(employee);
        //When
        Employee actual = employeeService.saveEmployee(employee,null);
        //Then
        verify(idService).createId(); // id  drwst bkait
      verify(employeeRepository).save(employee); //krekarakan la REpo xazn bkait
        assertThat(actual).isEqualTo(employee); // krekara xazn krawakanman bdaitawa
    }
   @Test
    void updateEmployeeWhenEmployeeIdExists() {
        //Given
        when(employeeRepository.save(employee))
                .thenReturn(employee);
        //When
        Employee actual = employeeService.update(employee);
        //Then
        Employee expected = employee;
        verify(employeeRepository).save(employee); // Repo aktu
        assertThat(actual).isEqualTo(expected);//AkK Mit ZÜ
    }

    @Test
    void deleteEmployeeWhenEmployeeIdExists() {
      employeeRepository.save(employee);
       employeeService.deleteEmployee(employee.id());
        verify(employeeRepository).deleteById (employee.id()); // Repo MIt LÖ
    }
}


