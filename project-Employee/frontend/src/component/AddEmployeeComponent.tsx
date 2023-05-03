import "./AddEmployeeComponent.css"
import React, {FormEvent, useEffect, useState} from "react";
import {employee, NewEmployee} from "../model/employee";
import {Link, useNavigate} from "react-router-dom";
type SaveEmployeeProps = {
    addEmployee: (newEmployee: NewEmployee) => void
}
export default function AddEmployeeComponent(props: SaveEmployeeProps) {
    //variables and method to collect and store inputs
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const navigate = useNavigate();
    const newEmployee: NewEmployee = {"firstName": firstName, "lastName": lastName, "email":email}

    /* send data to api and navigate when successful */
    function onSaveEmployee(error: React.MouseEvent<HTMLButtonElement>)  {
        error.preventDefault();
        if (newEmployee.firstName !== "" && newEmployee.lastName !== "" && newEmployee.email) {
            props.addEmployee(newEmployee)
            navigate('/employee')
        } else {
            alert("Please,fill in all inputs");
        }
    }
     return (
    <div>
        <div className={"container"} >
                    <div className={"Row"}>
                        <div className={"card"}>
                            <h2 className={"text"}>Add Employee</h2>
                        <form>
                            <div className={"FormGroup"}>
                                <input className={"FormControl"} value={firstName}
                                       type={"text"} placeholder='Enter First Name'
                                       onChange={(event ) => setFirstName(event.target.value)}/>

                            </div>
                            <div className={"FormGroup"}>
                                <input className={"FormControl"}
                                       value={lastName}
                                       type={"text"} placeholder='Enter Last Name'
                                       onChange={(event ) => setLastName(event.target.value)}/>

                            </div>
                            <div className={"FormGroup"}>
                                <input className={"FormControl"}
                                       value={email}
                                       type={"email"} placeholder=
                                           'Enter Email'
                                       onChange={(event ) => setEmail(event.target.value)}/>
                            </div>
              <button className={"save"} onClick={(error)=>onSaveEmployee(error)}>Save</button>
                            <Link to={"/employee"} className={"cancel"} >Cancel</Link>
                        </form>
                    </div>
                        </div>
                    </div>
        </div>
)
}

