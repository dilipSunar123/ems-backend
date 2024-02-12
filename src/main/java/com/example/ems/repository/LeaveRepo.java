package com.example.ems.repository;

import com.example.ems.entity.EmployeeEntity;
import com.example.ems.entity.LeaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepo extends JpaRepository<LeaveEntity, Integer> {

    public List<LeaveEntity> findByEmployeeEntityEmpId (int empId);

}
