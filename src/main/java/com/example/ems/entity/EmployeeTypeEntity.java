package com.example.ems.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employee_type")
public class EmployeeTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeTypeId;
    private String employeeType;

    public int getemployeeTypeId() {
        return employeeTypeId;
    }

    public void setemployeeTypeId(int employeeTypeId) {
        this.employeeTypeId = employeeTypeId;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    @Override
    public String toString() {
        return "EmployeeTypeEntity{" +
                "slno=" + employeeTypeId +
                ", employeeType='" + employeeType + '\'' +
                '}';
    }
}
