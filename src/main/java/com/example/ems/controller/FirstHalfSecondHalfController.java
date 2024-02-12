package com.example.ems.controller;

import com.example.ems.entity.FirstOrSecondHalfEntity;
import com.example.ems.repository.FirstHalfSecondHalfRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FirstHalfSecondHalfController {

    @Autowired
    private FirstHalfSecondHalfRepo repo;

    @GetMapping("/getFirstOrSecondHalf")
    private List<FirstOrSecondHalfEntity> getFirstOrSecondHalf() {
        return repo.findAll();
    }

}
