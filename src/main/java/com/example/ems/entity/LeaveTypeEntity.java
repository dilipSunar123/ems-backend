package com.example.ems.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_type")
public class LeaveTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveTypeId;

    @Column(name = "leaveType")
    private String leaveType;

    public int getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(int leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }
}
