package com.example.ems.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "leaveList")
public class LeaveList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empId")
    private EmployeeEntity employeeEntity;

    private float sickLeave;
    private float paidLeave;
    private float unpaidLeave;
    private float floaterLeave;

    private float maternityLeave;
    private float paternityLeave;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    public float getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(float sickLeave) {
        this.sickLeave = sickLeave;
    }

    public float getPaidLeave() {
        return paidLeave;
    }

    public void setPaidLeave(float paidLeave) {
        this.paidLeave = paidLeave;
    }

    public float getUnpaidLeave() {
        return unpaidLeave;
    }

    public void setUnpaidLeave(float unpaidLeave) {
        this.unpaidLeave = unpaidLeave;
    }

    public float getFloaterLeave() {
        return floaterLeave;
    }

    public void setFloaterLeave(float floaterLeave) {
        this.floaterLeave = floaterLeave;
    }

    public float getMaternityLeave() {
        return maternityLeave;
    }

    public void setMaternityLeave(float maternityLeave) {
        this.maternityLeave = maternityLeave;
    }

    public float getPaternityLeave() {
        return paternityLeave;
    }

    public void setPaternityLeave(float paternityLeave) {
        this.paternityLeave = paternityLeave;
    }

    @Override
    public String toString() {
        return "LeaveList{" +
                "id=" + id +
                ", employeeEntity=" + employeeEntity +
                ", sickLeave=" + sickLeave +
                ", paidLeave=" + paidLeave +
                ", unpaidLeave=" + unpaidLeave +
                ", floaterLeave=" + floaterLeave +
                ", maternityLeave=" + maternityLeave +
                ", paternityLeave=" + paternityLeave +
                '}';
    }
}
