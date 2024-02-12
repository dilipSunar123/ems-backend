package com.example.ems.repository;

import com.example.ems.entity.HalfDayFullDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HalfDayFullDayRepo extends JpaRepository<HalfDayFullDayEntity, Integer> {
}
