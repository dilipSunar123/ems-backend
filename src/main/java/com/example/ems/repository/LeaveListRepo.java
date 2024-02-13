package com.example.ems.repository;

import com.example.ems.entity.LeaveList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveListRepo extends JpaRepository<LeaveList, Integer> {


    public LeaveList findByEmployeeEntityEmpId(int empId);


}
