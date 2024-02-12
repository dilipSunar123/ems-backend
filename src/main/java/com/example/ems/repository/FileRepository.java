package com.example.ems.repository;

import com.example.ems.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    List<FileEntity> findByEmployeeEntityEmpIdAndFileTypeEntityFileTypeId(int empId, int fileTypeId);

    List<FileEntity> findByEmployeeEntityEmpId(int empId);

}