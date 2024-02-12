package com.example.ems.entity;

import jakarta.persistence.*;

@Entity(name = "feedback")
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;

    @Column(name = "rating")
    private int rating;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "task")
    private String task;

    // whom the manager assigned task to (employee's id)
    @ManyToOne
    @JoinColumn(name = "emp_id")
    private EmployeeEntity employeeEntity;

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "FeedbackEntity{" +
                "feedbackId=" + feedbackId +
                ", rating=" + rating +
                ", feedback='" + feedback + '\'' +
                ", task='" + task + '\'' +
                ", employeeEntity=" + employeeEntity +
                '}';
    }
}
