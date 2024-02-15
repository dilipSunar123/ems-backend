package com.example.ems.entity;


import jakarta.persistence.*;

@Entity(name = "total_leaves")
public class TotalLeaves {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int totalLeaveId;

    private float sickLeave;
    private float paidLeave;
    private float unpaidLeave;
    private float floaterLeave;

    private float maternityLeave;
    private float paternityLeave;

    private float casualLeave;


    @OneToOne
    @JoinColumn(name = "empId")
    private EmployeeEntity employeeEntity;

    public int getTotalLeaveId() {
        return totalLeaveId;
    }

    public void setTotalLeaveId(int totalLeaveId) {
        this.totalLeaveId = totalLeaveId;
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

    public float getCasualLeave() {
        return casualLeave;
    }

    public void setCasualLeave(float casualLeave) {
        this.casualLeave = casualLeave;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    @Override
    public String toString() {
        return "TotalLeaves{" +
                "totalLeaveId=" + totalLeaveId +
                ", sickLeave=" + sickLeave +
                ", paidLeave=" + paidLeave +
                ", unpaidLeave=" + unpaidLeave +
                ", floaterLeave=" + floaterLeave +
                ", maternityLeave=" + maternityLeave +
                ", paternityLeave=" + paternityLeave +
                ", casualLeave=" + casualLeave +
                ", employeeEntity=" + employeeEntity +
                '}';
    }
}
