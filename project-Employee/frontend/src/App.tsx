import React, {useEffect, useState} from 'react';
import './App.css';
import HeaderComponent from "./component/HeaderComponent"
import FooterComponent from "./component/FooterComponent";
import AddEmployeeComponent from "./component/AddEmployeeComponent";
import ListEmployeeComponent from "./component/ListEmployeeComponent";
import { BrowserRouter , Routes ,Route } from "react-router-dom";
import {employee, NewEmployee} from "./model/employee";
import axios from "axios";
import LoginPage from "./LoginPage";
import useUser from "./useUser";
import ProtectedRoutes from "./ProtectedRoutes";
function App() {
    const {user, login} = useUser()
    const [employees,setEmployees] = useState<employee[]>([])
const [url,setUrl] = useState()
//F
    useEffect(() => {
        loadAllEmployees()
    },[])
    function loadAllEmployees(){
        axios.get("/api/employees")
            .then((getAllEmployeesResponse) =>{setEmployees(getAllEmployeesResponse.data)})
            .catch((error)=> {console.error(error)})
    }//F
    function addEmployee(newEmployee: NewEmployee) {
        axios.post("/api/employees",newEmployee)
            .then((addEmployeeResponse) =>{
                setEmployees([addEmployeeResponse.data,...employees])
            })
            .catch(console.error)
    }//F
    function updateEmployee(employee: employee) {
        axios.put(`/api/employees/${employee.id}`, employee)
            .then((putEmployeeResponse) => {
                setEmployees(employees.map(currentEmployee => {
                    if (currentEmployee.id === employee.id) {
                        return putEmployeeResponse.data
                    }
                    else {
                        return currentEmployee
                    }
                }))
            })
            .catch(console.error)
    }
    function deleteEmployee(id:string){
        axios.delete('/api/employees/'+id)
            .then(() => {
                setEmployees(employees.filter((employees)=> employees.id !== id))
            })
            .catch(console.error)
    }
    return (
        <BrowserRouter>
            <HeaderComponent />
            <div className="container">
                <Routes>
                    <Route path="/" element={<ListEmployeeComponent employees={employees} deleteEmployee={deleteEmployee} />}  />
                    <Route path="/employees" element={<ListEmployeeComponent employees={employees} deleteEmployee={deleteEmployee} />} />
                    <Route path="/add-employee" element={<AddEmployeeComponent addEmployee={addEmployee}
                                                                               updateEmployee={updateEmployee}
                                                                                  />} />
                    <Route path="/add-employee/:id" element={<AddEmployeeComponent addEmployee={addEmployee}
                                                                                   updateEmployee={updateEmployee}
                                                                                     />} />
                    <Route path='/login' element={<LoginPage onLogin={login}  />}/>
                <Route element={<ProtectedRoutes user={user} />}></Route>
                </Routes>
            </div>
            <FooterComponent />
        </BrowserRouter>
  )
}

export default App;
