package com.example.ems.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
public class AttendanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int slno;

    @Column(name = "login_date_and_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime loginDateAndTime;

    @Column(name = "logout_date_and_time")
    private LocalDateTime logout_date_and_time;

    @Column(name = "location")
    private String location;

    // if someone is on leave, date will be shown
    @Column(name = "date")
    private String date;

    @Column(name = "gross_hour")
    private String grossHour;

    @Column(name = "log")
    private String log;

    @ManyToOne
    @JoinColumn(name = "workFromId")
    private WorkFromEntity workFromEntity;

//    @Column(name = "onTime")
//    private boolean onTime;

//    @Column(name = "late_arrival")
//    private boolean lateArrival;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emp_id", nullable = false)
    private EmployeeEntity employeeEntity;

    public int getSlno() {
        return slno;
    }

    public LocalDateTime getLoginDateAndTime() {
        return loginDateAndTime;
    }

    public LocalDateTime getLogout_date_and_time() {
        return logout_date_and_time;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getGrossHour() {
        return grossHour;
    }

    public void setGrossHour(String grossHour) {
        this.grossHour = grossHour;
    }

    //    public boolean isOnTime() {
//        return onTime;
//    }

//    public void setOnTime(boolean onTime) {
//        this.onTime = onTime;
//    }

//    public boolean isLateArrival() {
//        return lateArrival;
//    }

//    public void setLateArrival(boolean lateArrival) {
//        this.lateArrival = lateArrival;
//    }

    public void setSlno(int slno) {
        this.slno = slno;
    }

    public void setLoginDateAndTime(LocalDateTime loginDateAndTime) {
        this.loginDateAndTime = loginDateAndTime;
    }

    public void setLogout_date_and_time(LocalDateTime logout_date_and_time) {
        this.logout_date_and_time = logout_date_and_time;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public WorkFromEntity getWorkFromEntity() {
        return workFromEntity;
    }

    public void setWorkFromEntity(WorkFromEntity workFromEntity) {
        this.workFromEntity = workFromEntity;
    }

    public String getLeaveDate() {
        return date;
    }

    public void setLeaveDate(String leaveDate) {
        this.date = leaveDate;
    }

    @Override
    public String toString() {
        return "AttendanceEntity{" +
                "slno=" + slno +
                ", loginDateAndTime=" + loginDateAndTime +
                ", logout_date_and_time=" + logout_date_and_time +
                ", location='" + location + '\'' +
                ", leaveDate='" + date + '\'' +
                ", grossHour='" + grossHour + '\'' +
                ", log='" + log + '\'' +
                ", workFromEntity=" + workFromEntity +
                ", employeeEntity=" + employeeEntity +
                '}';
    }
}
