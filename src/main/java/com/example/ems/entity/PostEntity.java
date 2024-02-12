package com.example.ems.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Column(name = "post")
    private String post;

    @ManyToOne
    @JoinColumn(name = "empId")
    private EmployeeEntity employeeEntity;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    @Override
    public String toString() {
        return "PostEntity{" +
                "postId=" + postId +
                ", post='" + post + '\'' +
                ", employeeEntity=" + employeeEntity +
                '}';
    }
}
