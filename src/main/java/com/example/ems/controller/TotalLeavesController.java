package com.example.ems.controller;

import com.example.ems.entity.TotalLeaves;
import com.example.ems.repository.TotalLeavesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TotalLeavesController {

    @Autowired
    TotalLeavesRepo repo;

    @GetMapping("/getTotalLeavesOfAnEmp/{empId}")
    private TotalLeaves getTotalLeavesOfAnEmp (@PathVariable int empId) {

        return repo.findByEmployeeEntityEmpId(empId);

    }

}
