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

    private int sickLeave;
    private int paidLeave;
    private int unpaidLeave;
    private int floaterLeave;

    private int maternityLeave;
    private int paternityLeave;

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

    public int getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(int sickLeave) {
        this.sickLeave = sickLeave;
    }

    public int getPaidLeave() {
        return paidLeave;
    }

    public void setPaidLeave(int paidLeave) {
        this.paidLeave = paidLeave;
    }

    public int getUnpaidLeave() {
        return unpaidLeave;
    }

    public void setUnpaidLeave(int unpaidLeave) {
        this.unpaidLeave = unpaidLeave;
    }

    public int getFloaterLeave() {
        return floaterLeave;
    }

    public void setFloaterLeave(int floaterLeave) {
        this.floaterLeave = floaterLeave;
    }

    public int getMaternityLeave() {
        return maternityLeave;
    }

    public void setMaternityLeave(int maternityLeave) {
        this.maternityLeave = maternityLeave;
    }

    public int getPaternityLeave() {
        return paternityLeave;
    }

    public void setPaternityLeave(int paternityLeave) {
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
