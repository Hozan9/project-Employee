import axios from "axios";
import {toast} from "react-toastify";
import {FormEvent, ReactElement, useEffect, useState} from "react";
import {employee, NewEmployee} from "../model/employee";
import "./AddEmployeeComponent"
const BASE_URL = "http://localhost:8080/employee";
export default function  EmployeeService (){
    const [allEmployees, setAllEmployees] = useState<employee[]>(
        [])
    const [employees,setEmployees] = useState<employee[]>([])

        useEffect(() => {
                getAllEmployees()
            }, []);

    //**Method to get all employee from our api or database*/
        function getAllEmployees(): void {
            axios.get(BASE_URL)
                .then(response => setAllEmployees(response.data))
                .catch(() => toast.error("Loading page " +
                    "failed!\nTry again later"))
        }
        //Method to save employee
    function SaveEmployee(NewEmployee : NewEmployee)  {
        axios.post(BASE_URL, )
            .then((SaveEmployeeResponse) => {
                setEmployees([...employees, SaveEmployeeResponse.data])
            })
            .catch((error) => {
                toast.error("Unknown Error, try again later! " + error.response.statusText, {autoClose: 10000})
            })
    }

        return (
             <div>
            allEmployees: allEmployees

           </div>

        )
    return {employees,SaveEmployee}
    }
