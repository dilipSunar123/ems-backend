package com.example.ems.repository;

import com.example.ems.entity.LeaveTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveTypeRepo extends JpaRepository<LeaveTypeEntity, Integer> {
}
