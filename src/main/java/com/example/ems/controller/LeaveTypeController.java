package com.example.ems.controller;

import com.example.ems.entity.LeaveTypeEntity;
import com.example.ems.repository.LeaveTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class LeaveTypeController {

    @Autowired
    private LeaveTypeRepo leaveTypeRepo;

    @GetMapping("/findAllLeaveType")
    private List<LeaveTypeEntity> findAllLeaveType() {
        return leaveTypeRepo.findAll();
    }



}
