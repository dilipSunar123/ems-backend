package com.example.ems.controller;


import com.example.ems.entity.FileTypeEntity;
import com.example.ems.repository.FileTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class FileTypeController {

    @Autowired
    private FileTypeRepository repo;


    @GetMapping("/getAllFileType")
    private List<FileTypeEntity> getAllFileType() {
        return repo.findAll();
    }


}
