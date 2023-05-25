import  "./HeaderComponent.css"
import React, {useState} from "react";
import {NavLink} from "react-router-dom";
export default function HeaderComponent() {
    const [image] = useState<File>();
    return (
            <div className={"HeaderContainer"}>
                <div>
                <a className={"Employee"}> Employee Management System</a>
             <NavLink className={"LoginPage"} to="/login">
                Login </NavLink>
                </div>
                <p></p>
                <img src={image ? URL.createObjectURL(image): ""}
                     className="Img" width={"20"} />
            </div>
)
}