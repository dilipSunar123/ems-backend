package com.example.ems.controller;

import com.example.ems.entity.EmployeeEntity;
import com.example.ems.entity.InternalNoteEntity;
import com.example.ems.repository.EmployeeRepo;
import com.example.ems.repository.InternalNoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InternalNoteController {

    @Autowired
    private InternalNoteRepo repo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @GetMapping("/findNoteByEmployeeId/{empId}")
    private List<InternalNoteEntity> findNoteByEmployeeId (@PathVariable int empId)  {
        return repo.findByEmployeeEntityEmpId(empId);
    }

    @PostMapping("/addInternalNote/{empId}/{note}")
    private ResponseEntity<?> addInternalNote(@PathVariable int empId, @PathVariable String note) {

        EmployeeEntity employee = employeeRepo.getReferenceById(empId);

        InternalNoteEntity internalNote = new InternalNoteEntity();

        internalNote.setEmployeeEntity(employee);
        internalNote.setNote(note);

        repo.save(internalNote);

        return ResponseEntity.ok("Internal note saved!");

    }

    @DeleteMapping("/deleteNote/{empId}/{noteId}")
    private ResponseEntity<?> deleteNote(@PathVariable int empId, @PathVariable int noteId) {

        InternalNoteEntity noteEntity = repo.findByNoteIdAndEmployeeEntityEmpId(noteId, empId);

        repo.deleteById(noteEntity.getNoteId());

        return ResponseEntity.ok("note deleted successfully");
    }

    @PutMapping("/editNote/{empId}/{noteId}/{updatedNote}")
    private ResponseEntity<?> editNote(@PathVariable int empId, @PathVariable int noteId, @PathVariable String updatedNote) {

        InternalNoteEntity noteEntity = repo.findByNoteIdAndEmployeeEntityEmpId(noteId, empId);

        noteEntity.setNote(updatedNote);

        repo.save(noteEntity);

        return ResponseEntity.ok(noteEntity);

    }

}
