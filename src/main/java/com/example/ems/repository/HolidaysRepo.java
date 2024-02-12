package com.example.ems.repository;

import com.example.ems.entity.HolidaysEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidaysRepo extends JpaRepository<HolidaysEntity, Integer> {
}
