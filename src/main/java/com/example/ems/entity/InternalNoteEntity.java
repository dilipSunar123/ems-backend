package com.example.ems.entity;

import jakarta.persistence.*;

@Entity(name = "internal_note")
public class InternalNoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noteId;

    @Column(name = "note", nullable = false)
    private String note;

    @ManyToOne
    @JoinColumn(name = "empId")
    private EmployeeEntity employeeEntity;

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    @Override
    public String toString() {
        return "InternalNoteEntity{" +
                "noteId=" + noteId +
                ", note='" + note + '\'' +
                ", employeeEntity=" + employeeEntity +
                '}';
    }
}
