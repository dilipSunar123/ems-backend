package com.example.ems.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "leave")
public class LeaveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int slno;

    @Column(name = "start_date")
    private String startDate;    // start_date needs to be stored as String as JSON do not support LocalDateTime as a datatype

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "status")
    private String status;

    @Column(name = "reason")
    private String reason;

    @Column(name = "applied_on")
    private LocalDateTime appliedOn;

//    @ManyToOne
//    @JoinColumn(name = "halfday_or_fullday_id")
//    private HalfDayFullDayEntity halfDayFullDayEntity;

    @ManyToOne
    @JoinColumn(name = "empId")
    private EmployeeEntity employeeEntity;

    @ManyToOne
    @JoinColumn(name = "leaveTypeId")
    private LeaveTypeEntity leaveTypeEntity;


    @Column(name = "same_day")
    private String sameDay;

    @Column(name = "diff_day_start_in") // first half or second half
    private String diffDayStartIn;

    @Column(name = "diff_day_end_in") // first half or second half
    private String diffDayEndIn;


    public int getSlno() {
        return slno;
    }

    public void setSlno(int slno) {
        this.slno = slno;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(LocalDateTime appliedOn) {
        this.appliedOn = appliedOn;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    public LeaveTypeEntity getLeaveTypeEntity() {
        return leaveTypeEntity;
    }

    public void setLeaveTypeEntity(LeaveTypeEntity leaveTypeEntity) {
        this.leaveTypeEntity = leaveTypeEntity;
    }

    public String getSameDay() {
        return sameDay;
    }

    public void setSameDay(String sameDay) {
        this.sameDay = sameDay;
    }

    public String getDiffDayStartIn() {
        return diffDayStartIn;
    }

    public void setDiffDayStartIn(String diffDayStartIn) {
        this.diffDayStartIn = diffDayStartIn;
    }

    public String getDiffDayEndIn() {
        return diffDayEndIn;
    }

    public void setDiffDayEndIn(String diffDayEndIn) {
        this.diffDayEndIn = diffDayEndIn;
    }

    @Override
    public String toString() {
        return "LeaveEntity{" +
                "slno=" + slno +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                ", appliedOn=" + appliedOn +
                ", employeeEntity=" + employeeEntity +
                ", leaveTypeEntity=" + leaveTypeEntity +
                ", sameDay='" + sameDay + '\'' +
                ", diffDayStartIn='" + diffDayStartIn + '\'' +
                ", diffDayEndIn='" + diffDayEndIn + '\'' +
                '}';
    }
}
