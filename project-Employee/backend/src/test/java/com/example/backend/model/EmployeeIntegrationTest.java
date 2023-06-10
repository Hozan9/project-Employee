package com.example.backend.model;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import java.io.File;
import java.util.Map;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmployeeRepository employeeRepository;
      @Autowired
      ObjectMapper mapper;
      Employee dummyEmployee;
      String jsonEmployee;
    @MockBean
    Cloudinary cloudinary;
    Uploader uploader = mock(Uploader.class);
    Employee employee1 = new Employee("123", "hozan", "Mahmood", "hozan.bahaden.93@gmail.com", "");
    Employee employee2 = new Employee("456", "lani", "Pavel", "lani.bahaden.93@gmail.com","");
      @BeforeEach
     void setUp() throws Exception {
       dummyEmployee = new Employee("1","hozan","Mahmood","hozan.bahaden.93@gmail.com","");
     jsonEmployee = mapper.writeValueAsString(dummyEmployee);
    }
    @Test
    @DirtiesContext
    @WithMockUser
    void findAll_shouldReturnEmptyList_whenRepoIsEmpty() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                []
                """));
 }
    @Test
    @DirtiesContext
    @WithMockUser
    void findAll_shouldReturnAllEmployeeAvailable() throws Exception {
       employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                [
                                {
                               "id" :"123", 
                                "firstName":"hozan", 
                                "lastName":"Mahmood", 
                                "email":"hozan.bahaden.93@gmail.com",
                                "url": ""                           
                                },
                                {
                                 "id" :"456", 
                                "firstName":"lani", 
                                "lastName":"Pavel", 
                                "email":"lani.bahaden.93@gmail.com",
                                "url": ""                             
                                }
                                ]
                                """
                ));
    }
    @Test
    @DirtiesContext
    @WithMockUser
    void findById() throws Exception {
        employeeRepository.save(dummyEmployee);
        mockMvc.perform(get("/api/employees/" + dummyEmployee.id()))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonEmployee));
    }
    @Test
    @DirtiesContext
    @WithMockUser
    void saveEmployee_expectSuccessfulPost() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "filename.png", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());
        MockMultipartFile data = new MockMultipartFile("data", null, MediaType.APPLICATION_JSON_VALUE, """
                {
                 "id" :"123", 
                 "firstName":"hozan", 
                 "lastName":"Mahmood", 
                 "email":"hozan.bahaden.93@gmail.com",
                 "url": ""
                }
                """.getBytes());
        File fileToUpload = File.createTempFile("image", null);
        file.transferTo(fileToUpload);
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(), any())).thenReturn(Map.of("url", ""));
        String actual = mockMvc.perform(multipart("/api/employees")
                        .file(data)
                        .file(file)
                        .with(csrf())
                )
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                        {
                          "firstName":"hozan", 
                          "lastName":"Mahmood", 
                          "email":"hozan.bahaden.93@gmail.com",
                           "url": ""
                        }
                        """
                ))
                .andReturn()
                .getResponse()
                .getContentAsString();
        Employee actualEmployee = mapper.readValue(actual, Employee.class);
        assertThat(actualEmployee.id())
                .isNotBlank();
    }
    @Test
    @DirtiesContext
    @WithMockUser
    void updateEmployeeCorrectExpectUpdatedEmployee() throws Exception {
        employeeRepository.save(dummyEmployee);
        Employee toUpdateEmployee = new Employee(dummyEmployee.id(), "pavel", "habib", "bamo.habib@gmail.com","");
        String jsonModifiedEmployee = mapper.writeValueAsString(toUpdateEmployee);
        mockMvc.perform(put("/api/employees/" + dummyEmployee.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonModifiedEmployee)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonModifiedEmployee));
        Optional<Employee> actual = employeeRepository.findById(dummyEmployee.id());
        assertThat(actual).contains(toUpdateEmployee);
    }
    @Test
    @DirtiesContext
    @WithMockUser
    void updateEmployeeCreated_whenEmployeeDoesntExist() throws Exception {
        String responseJson =
                mockMvc.perform(put("/api/employees/" + dummyEmployee.id())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonEmployee)
                        .with(csrf()))
                        .andExpect(status().isOk())
                        .andExpect(content().json("""
                                {
                                      "id" :"1",
                                   "firstName":"hozan",
                                  "lastName":"Mahmood",
                                 "email":"hozan.bahaden.93@gmail.com",
                                 "url": "" 
                                }
                                """))
                        .andExpect(jsonPath("$.id").isNotEmpty())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
      Employee actual = mapper.readValue(responseJson, Employee.class);
        Employee expected = new Employee(
                actual.id(),
                dummyEmployee.firstName(),
                dummyEmployee.lastName(),
                dummyEmployee.email(),
                dummyEmployee.url());
        assertThat(employeeRepository.findAll()).contains(expected);
    }
    @Test
    @DirtiesContext
   @WithMockUser
    void deleteEmployee() throws Exception {
        employeeRepository.save(dummyEmployee);
        mockMvc.perform(delete("/api/employees/" + dummyEmployee.id())
                .with(csrf()))
                .andExpect(status().isOk());
        assertThat(employeeRepository.findAll()).doesNotContain(dummyEmployee);
        mockMvc.perform(delete("/api/employees/" + dummyEmployee.id())
                .with(csrf()))
                .andExpect(status().isOk());
    }
}