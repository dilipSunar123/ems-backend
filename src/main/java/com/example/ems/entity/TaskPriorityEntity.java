package com.example.ems.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "task_priority")
public class TaskPriorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int priorityId;

    @Column(name = "priority_level")
    private String priorityLevel;

    public int getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    @Override
    public String toString() {
        return "TaskPriorityEntity{" +
                "priorityId=" + priorityId +
                ", priorityLevel='" + priorityLevel + '\'' +
                '}';
    }
}
