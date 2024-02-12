package com.example.ems.repository;

import com.example.ems.entity.FileTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileTypeRepository extends JpaRepository<FileTypeEntity, Integer> {
}
