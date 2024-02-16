package com.example.ems.controller;

import com.example.ems.entity.TotalLeaves;
import com.example.ems.repository.TotalLeavesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class TotalLeavesController {

    @Autowired
    TotalLeavesRepo repo;

    @GetMapping("/getTotalLeavesOfAnEmp/{empId}")
    private TotalLeaves getTotalLeavesOfAnEmp (@PathVariable int empId) {

        return repo.findByEmployeeEntityEmpId(empId);

    }

}
