package com.example.backend.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    EmployeeRepository employeeRepository;

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

// Wenn wir ein Employee an den Controller senden, wird es erstellt.
// Dieses neu erstellte Employee erwarten wir als Antwort
@Test
@DirtiesContext
    void saveEmployee_shouldReturnCreatedEmployee() throws Exception {
            mockMvc.perform(
            post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            // Was im Body beim POST-Request verschickt wird
            .content(
            """
                                                 {
                                                     "id": "1",
                                                     "firstName": "hozan",
                                                     "lastName": "Mahmood",
                                                     "email": "hozan.bahaden.93@gmail.com"
                                                 }
                                                """
            ))
            .andExpect(
            status().isOk()
            )
            .andExpect(
            content().json(
            """
                                         {
                                            "id": "1",
                                                     "firstName": "hozan",
                                                     "lastName": "Mahmood",
                                                     "email": "hozan.bahaden.93@gmail.com"
                                         }
                                        """
            ));
            }
            }



















































/*


    @Test
    void findAll_ShouldListWithOneEmployee_WhenRepoOneEmployee() throws  Exception{
        employeeRepository.save(Employee);
    }
    @Test
    @DirtiesContext
    @WithMockUser
    void saveEmployee_expectEmployeeInRepository() throws Exception{
        String  responseJson =
                mockMvc.perform(
                                post("/api/employees")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(jsonEmployee))
                        .with(csrf())
                        .andExpect(status().isCreated())
                        .andExpect(content().json("""
{
"firstName" : "Lani",
"lastName" : "lani",
"email" : "lani@gmail.com",
"url": "url"

}
"""))
                        .andExpect(jsonPath("$.id").isNotEmpty())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
        Employee actual = mapper.readValue(responseJson, Employee.class);
        assertThat(employeeRepository.findAll()).contains(actual);


    }

*/









//   void postEmployee() throws Exception {
     //   mockMvc.perform(post("/api/employees")
              //          .contentType(MediaType.APPLICATION_JSON)
              //          .content("""
//{
                   //    "firstName": "hozan",
                     //  "lastName": "mahmood",
                       //"email": "hozan.mahmood@gmail.com"
//}
//"""
                        /*        ))
                        .andExpect(status().isCreated())
                        .andExpect(content().json("""
                                {
                       "firstName": "hozan",
                       "lastName": "mahmood",
                       "email": "hozan.mahmood@gmail.com"
    }
"""))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

      /*  List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(1);
        assertThat(employees.get(0).firstName()).isEqualTo("hozan");
        assertThat(employees.get(0).lastName()).isEqualTo("mahmood");
        assertThat(employees.get(0).email()).isEqualTo("hozan.mahmood@gmail.com");
    }*/



/*xom
   @Test
    @DirtiesContext
    void getAll_shouldReturnAllEmployees() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().json(
                        """
[]
"""
                ));
        }
//bashy 3
    @Test
    @DirtiesContext
    void getEmployee_shouldReturnAllEmployeeAdded() throws Exception {
        Employee employee = new Employee("1","hozan","mahmood","hozan.mahmood@gmail.com");
        employeeRepository.save(employee);
        Employee employee1 = new Employee("2","lani","lani","hozan.lani@gmail.com");
        employeeRepository.save(employee1);
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().json(

"""
                                [
                                {
                                "id": "1",
                                "firstName": "hozan",
                                "lastName": "mahmood",
                                "email": "hozan.mahmood@gmail.com"
                                },
                                {
                                "id": "2",
                                "firstName": "lani",
                                "lastName": "lani",
                                "email": "hozan.lani@gmail.com"
                                }
                                ]
                                """
        ));
    }
    @Test
    @DirtiesContext
    void addEmployee_shouldReturnAddedEmployee() throws Exception {
        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {
                                "id": "12345",
                                "firstName": "bali",
                                "lastName": "bali",
                                "email": "nali@gmail.com"
                                }
                                """
                )
                )
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                "id": "12345",
                                "firstName": "bali",
                                "lastName": "bali",
                                "email": "nali@gmail.com"
                                }
                                """
                ));
    }

    @Test
    @DirtiesContext
    void getEmployeeById_shouldReturnEmployeeWithId() throws Exception {
        Employee employee = new Employee("12","pavel","pave","pavel@gmail.com");
        employeeRepository.save(employee);

        mockMvc.perform(get ("/api/employee/12"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().json(
                        """
                                    {
                                  "id": "12",
                                "firstName": "pavel",
                                "lastName": "pave",
                                "email": "pavel@gmail.com"
                                }
                                    """
                ));
    }
    }xom*/




