package com.example.ems.controller;

import com.example.ems.entity.EmployeeEntity;
import com.example.ems.repository.EmployeeTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class EmployeeTypeController {

    @Autowired
    private EmployeeTypeRepo employeeTypeRepo;

}
