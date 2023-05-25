import {NavLink} from "react-router-dom";
import "./ListEmployeeComponent.css"
import {employee} from "../model/employee";
import "./AddEmployeeComponent"
type Props ={
    employees:employee[],
    deleteEmployee:(id: string) => void
}
export default function ListEmployeeComponent(props: Props) {

    function onDeleteClick( id:string) {
        props.deleteEmployee(id)
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
                    {props.employees.map((employee) =>
                        <tr id={employee.id } key={employee.id}>
                            <td className={"td"}>{employee.id} </td>
                            <td className={"td"}>{employee.firstName}</td>
                            <td className={"td"}>{employee.lastName}</td>
                            <td className={"td"}>{employee.email}</td>
                            <td>
                                <NavLink className={"update"} to ={`/add-employee/${employee.id}`}>Update</NavLink>
                                <button  className={"delete"}
                                         onClick={()=>onDeleteClick(employee.id)}
                                >Delete</button>
                            </td>
                        </tr>
                    )}
                    </tbody>
              </table>
            </div>)}


