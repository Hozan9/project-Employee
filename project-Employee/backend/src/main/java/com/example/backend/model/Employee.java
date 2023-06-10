package com.example.backend.model;

import org.springframework.data.annotation.Id;

public  record Employee(

              @Id
                 String id,
                 String firstName,
                 String lastName,
                 String email,
                 String url
        ){

    public Employee withUrl(String url){
        return new Employee
                (id,firstName,lastName,email,url);
    }

}

