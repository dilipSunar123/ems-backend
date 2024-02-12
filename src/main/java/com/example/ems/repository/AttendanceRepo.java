package com.example.ems.repository;

import com.example.ems.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepo extends JpaRepository<AttendanceEntity, Integer> {

    public List<AttendanceEntity> findByEmployeeEntityEmpId(int empId);
    List<AttendanceEntity> findByEmployeeEntityEmpIdOrderByLoginDateAndTimeDesc(int empId);

//    AttendanceEntity findByEmployeeEntityEmpId(int empId);

//    List<AttendanceEntity> findByEmployeeEntityEmpId (int empId);

}
