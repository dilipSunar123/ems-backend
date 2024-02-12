package com.example.ems.repository;

import com.example.ems.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<PostEntity, Integer> {

    List<PostEntity> findByEmployeeEntityEmpId(int empId);

}
