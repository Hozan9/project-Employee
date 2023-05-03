package com.example.backend.model;

import org.springframework.data.annotation.Id;

public  record Employee(

              @Id
                 String id,
                 String firstName,
                 String lastName,
                String email
        ){}

