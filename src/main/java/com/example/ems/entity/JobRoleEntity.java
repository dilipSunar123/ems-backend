package com.example.ems.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "jobs")
public class JobRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int job_id;

    @Column(nullable = false)
    String job_role;

    public int getJob_id() {
        return job_id;
    }

    public String getJob_role() {
        return job_role;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public void setJob_role(String job_role) {
        this.job_role = job_role;
    }

    @Override
    public String toString() {
        return "JobRoleEntity{" +
                "job_id=" + job_id +
                ", job_role='" + job_role + '\'' +
                '}';
    }
}
