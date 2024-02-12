package com.example.ems.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fileType")
public class FileTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileTypeId;

    @Column(name = "fileType", nullable = false)
    private String fileType;

    public int getFileTypeId() {
        return fileTypeId;
    }

    public void setFileTypeId(int fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
