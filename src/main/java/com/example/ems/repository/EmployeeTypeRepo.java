package com.example.ems.repository;

import com.example.ems.entity.EmployeeEntity;
import com.example.ems.entity.EmployeeTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeTypeRepo extends JpaRepository<EmployeeTypeEntity, Integer> {

    public List<EmployeeEntity> findByEmployeeTypeId(int empTypeId);

}
