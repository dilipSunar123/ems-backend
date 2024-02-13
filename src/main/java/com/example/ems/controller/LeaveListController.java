package com.example.ems.controller;


import com.example.ems.entity.LeaveList;
import com.example.ems.repository.LeaveListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LeaveListController {


    @Autowired
    private LeaveListRepo leaveListRepo;

    @GetMapping("/findAllBalances/{empId}")
    private String findAllBalances (@PathVariable int empId) {

        LeaveList leaveLists = leaveListRepo.findByEmployeeEntityEmpId(empId);

        return "Floater Leave : " + leaveLists.getFloaterLeave() +
                "\nMaternity Leave : " + leaveLists.getMaternityLeave() +
                "\nPaid Leave : " + leaveLists.getPaidLeave() +
                "\nPaternity Leave : " + leaveLists.getPaternityLeave() +
                "\nSick Leave : " + leaveLists.getSickLeave() +
                "\nUnpaid Leave : " + leaveLists.getUnpaidLeave() +
                "\nCasual Leave : ";
    }



}
