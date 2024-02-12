package com.example.ems.repository;

import com.example.ems.entity.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepo extends JpaRepository<FeedbackEntity, Integer> {
    public List<FeedbackEntity> findByEmployeeEntityEmpId(int empId);
}
