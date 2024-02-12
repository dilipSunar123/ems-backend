package com.example.ems.repository;

import com.example.ems.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<DepartmentEntity, Integer> {
}
