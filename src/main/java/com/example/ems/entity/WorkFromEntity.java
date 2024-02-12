package com.example.ems.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "work_from")
public class WorkFromEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int workFromId;

    @Column(name = "work_from")
    private String workFrom;

    public int getWorkFromId() {
        return workFromId;
    }

    public void setWorkFromId(int workFromId) {
        this.workFromId = workFromId;
    }

    public String getWorkFrom() {
        return workFrom;
    }

    public void setWorkFrom(String workFrom) {
        this.workFrom = workFrom;
    }

    @Override
    public String toString() {
        return "WorkFromEntity{" +
                "workFromId=" + workFromId +
                ", workFrom='" + workFrom + '\'' +
                '}';
    }
}
