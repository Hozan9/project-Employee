import  "./HeaderComponent.css"
import  React from "react";
import {NavLink} from "react-router-dom";
export default function HeaderComponent() {

    return (
        <div className={"HeaderComponent"}>
            <div className={"HeaderContainer"}>
                <a> Employee Management System</a>
             <NavLink className={"LoginPage"} to="/login">
                Login </NavLink>
            </div>
        </div>

)
}