package com.example.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FirstHalfSecondHalfRepo extends JpaRepository<FirstOrSecondHalfEntity, Integer> {
}
