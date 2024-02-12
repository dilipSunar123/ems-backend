package com.example.ems.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "department")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int deptId;
    @Column(nullable = false)
    private String dept_name;
    @Column(nullable = false)
    private String location;

    public int getDeptId() {
        return deptId;
    }

    public String getDept_name() {
        return dept_name;
    }

    public String getLocation() {
        return location;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "DepartmentEntity{" +
                "dept_id=" + deptId +
                ", dept_name='" + dept_name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
