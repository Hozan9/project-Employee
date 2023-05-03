import React, {useEffect, useState} from "react";
import {NavLink} from "react-router-dom";
import "./ListEmployeeComponent.css"
import {employee} from "../model/employee";
import {toast} from "react-toastify";
import axios from "axios";
export default function ListEmployeeComponent(props: { employee: employee[] }) {
         const [employeeArray, setEmployeeArray] = useState<employee[]>([])

         useEffect(() => {
                 getEmployeeArray()
             }, [])

              function getEmployeeArray(): void {
              axios.get('/api/employees')
                  .then(response => {
                      setEmployeeArray([...employeeArray, response.data])
                      toast.success("Successfully added!")

              })
                      .catch(() => toast.error("Failed to add product!"))

              }

          return (
            <div className={"container"}>
                <NavLink className={"AddEmployee"} to={"/add-employee"}>
                    Add Employee </NavLink>
                    <h2 className={"ListEmployee"}>List Employee</h2>

              <table className={"table"}>
                    <thead className={"tableHead"}>
                    <tr>
                    <th className={"th"}> ID</th>
                    <th className={"th"}>First Name</th>
                    <th className={"th"}> Last Name</th>
                    <th className={"th"}> Email</th>
                    <th className={"th"}>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {props.employee.map((employee) =>
                        <tr id={employee.id } key={employee.id}>
                            <td className={"td"}>{employee.id} </td>
                            <td className={"td"}>{employee.firstName}</td>
                            <td className={"td"}>{employee.lastName}</td>
                            <td className={"td"}>{employee.email}</td>
                            <td>
                                <button  >Update</button> {""}
                                <button  >Delete</button>
                            </td>
                        </tr>
                    )}
                    </tbody>
              </table>
            </div>)}


