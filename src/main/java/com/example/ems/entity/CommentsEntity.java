package com.example.ems.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "post_comments")
public class CommentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "postId")
    private PostEntity postEntity;

    @ManyToOne
    @JoinColumn(name = "empId")
    private EmployeeEntity employeeEntity;


    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PostEntity getPostEntity() {
        return postEntity;
    }

    public void setPostEntity(PostEntity postEntity) {
        this.postEntity = postEntity;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    @Override
    public String toString() {
        return "CommentsEntity{" +
                "commentId=" + commentId +
                ", comment='" + comment + '\'' +
                ", postEntity=" + postEntity +
                ", employeeEntity=" + employeeEntity +
                '}';
    }
}
