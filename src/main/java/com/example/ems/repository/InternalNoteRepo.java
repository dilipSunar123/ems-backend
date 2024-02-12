package com.example.ems.repository;

import com.example.ems.entity.InternalNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternalNoteRepo extends JpaRepository<InternalNoteEntity, Integer> {

    public List<InternalNoteEntity> findByEmployeeEntityEmpId (int empId);

    public InternalNoteEntity findByNoteIdAndEmployeeEntityEmpId(int noteId, int empId);
}
