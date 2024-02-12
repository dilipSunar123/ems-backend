package com.example.ems.controller;

import com.example.ems.entity.DepartmentEntity;
import com.example.ems.repository.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    DepartmentRepo departmentRepo;

    @GetMapping("/getAllDepartments")
    private List<DepartmentEntity> getAllDepartments() {
        return departmentRepo.findAll();
    }

    @PostMapping("/addDepartment")
    private ResponseEntity<?> addDepartment(@RequestBody DepartmentEntity entity) {

        List<DepartmentEntity> entityList = departmentRepo.findAll();

        for (DepartmentEntity list: entityList) {
            if (list.getDept_name().equals(entity.getDept_name())) {
                if (list.getLocation().equals(entity.getLocation())) {
                    return ResponseEntity.ok("Department already exists!");
                }
            }

        }
        departmentRepo.save(entity);

        return ResponseEntity.ok("Department added!");

    }

}
