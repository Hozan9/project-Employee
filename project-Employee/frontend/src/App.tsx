import React, {useEffect, useState} from 'react';
import './App.css';
import HeaderComponent from "./component/HeaderComponent"
import FooterComponent from "./component/FooterComponent";
import AddEmployeeComponent from "./component/AddEmployeeComponent";
import ListEmployeeComponent from "./component/ListEmployeeComponent";
import { BrowserRouter , Routes ,Route } from "react-router-dom";
import {employee, NewEmployee} from "./model/employee";
import axios from "axios";
function App() {
    const [employees,setEmployees] = useState<employee[]>([])

    useEffect(() => {
        loadAllEmployees()
    },[])
    function loadAllEmployees(){
        axios.get("/api/employees")
            .then((getAllEmployeesResponse) =>{setEmployees(getAllEmployeesResponse.data)})
            .catch((error)=> {console.error(error)})
    }
    function addEmployee(newEmployee: NewEmployee) {
        axios.post("/api/employees",newEmployee)
            .then((addEmployeeResponse) =>{
                setEmployees([...employees, addEmployeeResponse.data])
            })
            .catch(console.error)
    }

  return (
        <BrowserRouter>
            <HeaderComponent />
            <div className="container">
                <Routes>
                    <Route path="/" element={<ListEmployeeComponent employee={employees}/>}  />
                    <Route path="/employee" element={<ListEmployeeComponent employee={employees} />} />
                    <Route path="/add-employee" element={<AddEmployeeComponent addEmployee={addEmployee} />} />
                    <Route path="/add-employee/:id" element={<AddEmployeeComponent addEmployee={addEmployee}/>} />
                </Routes>

            </div>
            <FooterComponent />
        </BrowserRouter>

  );
}
export default App;
