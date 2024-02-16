package com.example.ems.controller;


import com.example.ems.entity.HolidaysEntity;
import com.example.ems.repository.HolidaysRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class HolidaysController {

    @Autowired
    private HolidaysRepo repo;

    @GetMapping("/findAllHolidays")
    private List<HolidaysEntity> findAllHolidays () {
        return repo.findAll();
    }

    @PostMapping("/addHoliday")
    private ResponseEntity<?> addHoliday (@RequestBody HolidaysEntity entity) {
        List<HolidaysEntity> list = repo.findAll();

        boolean holidayPresent = false;

        for (HolidaysEntity holidaysEntity: list) {
            if (entity.getName().equals(holidaysEntity.getName()))
                holidayPresent = true; break;
        }

        if (!holidayPresent) {
            repo.save(entity);

            return ResponseEntity.ok("Holiday added!");
        }
        return ResponseEntity.ok("Holiday already present!");
    }

}
