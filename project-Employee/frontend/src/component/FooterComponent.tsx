import React from "react"
import "./FooterComponent.css"
export default function FooterComponent(){
    return(
        <div>
              <footer className={"footer"}>
                  <span > All Right Reserved &copy;{new Date().getFullYear()}</span>
              </footer>
        </div>
    )
}