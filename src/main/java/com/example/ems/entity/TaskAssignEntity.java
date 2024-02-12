package com.example.ems.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "task_assign")
public class TaskAssignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;

    @Column(name = "task_title", nullable = false)
    private String taskTitle;

    @Column(name = "task_desc")
    private String taskDesc;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "status")
    private String status;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "empId")
//    private EmployeeEntity assignedBy;

    // stores manager ID
    @Column(name = "assigned_by")
    private int assignedBy;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empId")
    private EmployeeEntity employeeEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "priorityId")
    private TaskPriorityEntity taskPriorityEntity;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
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

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    public TaskPriorityEntity getTaskPriorityEntity() {
        return taskPriorityEntity;
    }

    public void setTaskPriorityEntity(TaskPriorityEntity taskPriorityEntity) {
        this.taskPriorityEntity = taskPriorityEntity;
    }


    public int getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(int assignedBy) {
        this.assignedBy = assignedBy;
    }

    @Override
    public String toString() {
        return "TaskAssignEntity{" +
                "taskId=" + taskId +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskDesc='" + taskDesc + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", assignedBy=" + assignedBy +
                ", employeeEntity=" + employeeEntity +
                ", taskPriorityEntity=" + taskPriorityEntity +
                '}';
    }
}
