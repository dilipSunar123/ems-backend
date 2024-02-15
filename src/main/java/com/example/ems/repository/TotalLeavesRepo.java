package com.example.ems.repository;

import com.example.ems.entity.TotalLeaves;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TotalLeavesRepo extends JpaRepository<TotalLeaves, Integer> {

    TotalLeaves findByEmployeeEntityEmpId(int empId);

}
