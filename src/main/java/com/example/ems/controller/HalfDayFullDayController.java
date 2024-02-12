package com.example.ems.controller;

import com.example.ems.entity.HalfDayFullDayEntity;
import com.example.ems.repository.HalfDayFullDayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HalfDayFullDayController {

    @Autowired
    private HalfDayFullDayRepo repo;

    @GetMapping("/findHalfDayOrFullDay")
    private List<HalfDayFullDayEntity> findHalfDayOrFullDay() {
        return repo.findAll();
    }

}
