package com.example.ems.repository;

import com.example.ems.entity.FirstOrSecondHalfEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirstHalfSecondHalfRepo extends JpaRepository<FirstOrSecondHalfEntity, Integer> {
}
