package com.example.ems.entity;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "files")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String fileName;

    @Lob
    @Column(nullable = false)
    private byte[] data;

    @Column(name = "status")
    private String status = "pending";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emp_id")
    private EmployeeEntity employeeEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fileTypeId")
    private FileTypeEntity fileTypeEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    public FileTypeEntity getFileTypeEntity() {
        return fileTypeEntity;
    }

    public void setFileTypeEntity(FileTypeEntity fileTypeEntity) {
        this.fileTypeEntity = fileTypeEntity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UploadedFile{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", data=" + Arrays.toString(data) +
                ", status=" + status +
                ", employeeEntity=" + employeeEntity +
                ", fileTypeEntity=" + fileTypeEntity +
                '}';
    }
}
