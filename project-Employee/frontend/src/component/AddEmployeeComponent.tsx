import "./AddEmployeeComponent.css"
import React, { useEffect, useState} from "react";
import {NewEmployee} from "../model/employee";
import {Link, useNavigate, useParams} from "react-router-dom";
import axios from "axios";

 type SaveEmployeeProps = {
    addEmployee: (newEmployee: NewEmployee) => void,
    updateEmployee: (updateEmployee: { firstName: string;
        lastName: string; id: string;
        email: string; url: string }) => void
 }
const BASE_URL = "/api/employees";

export default function AddEmployeeComponent(props: SaveEmployeeProps) {
    //variables and method to collect and store inputs
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const navigate = useNavigate();
    const newEmployee: NewEmployee = {"firstName": firstName,
        "lastName": lastName, "email": email}

   const [url] = useState<string>("")

    const {id} = useParams<{ id: string }>()
    function onSaveEmployee(error: React.MouseEvent<HTMLButtonElement>) {
        error.preventDefault();

        if (newEmployee.firstName !== "" && newEmployee.lastName !== "" && newEmployee.email) {
            if (id) {
                props.updateEmployee({id,firstName,lastName,email,url})
                navigate('/employee')
            } else {
                props.addEmployee(newEmployee);
                navigate('/employees')
            }
            }else{
                alert("Please,fill in all inputs");
            }
        }
    function title() {
        if (id) {
            return "Update Employee";
        } else {
            return "Add Employee";
        }
    }

    useEffect(() => {
        if (id) {
            loadEmployeeById(id)
        }
    },
        [id])

    function loadEmployeeById(id: string) {
         axios.get(`${BASE_URL}/${id}` )
            .then((response) => {
                setFirstName(response.data.firstName);
                setLastName(response.data.lastName);
                setEmail(response.data.email);
            })
            .catch(error => {
console.log(error);
alert(error)
            });
    }

    return (
        <div>
            <div className={"container"}>
                <div className={"Row"}>
                    <div className={"card"}>
                        <h2 className={"text"}>{title()}</h2>

                        <form>
                            <div className={"FormGroup"}>
                                <input className={"FormControl"} value={firstName}
                                       type={"text"} placeholder='Enter First Name'
                                       onChange={(event) => setFirstName(event.target.value)}/>

                            </div>
                            <div className={"FormGroup"}>
                                <input className={"FormControl"}
                                       value={lastName}
                                       type={"text"} placeholder='Enter Last Name'
                                       onChange={(event) => setLastName(event.target.value)}/>
                            </div>
                            <div className={"FormGroup"}>
                                <input className={"FormControl"}
                                       value={email}
                                       type={"email"} placeholder=
                                           'Enter Email'
                                       onChange={(event) => setEmail(event.target.value)}/>
                            </div>
                            <button className={"save"} onClick={(error) => onSaveEmployee(error)}>Save</button>
                            <Link to={"/employees"} className={"cancel"}>Back</Link>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    )
}

