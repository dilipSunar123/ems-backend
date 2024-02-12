package com.example.ems.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "likes")
public class LikesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likeId;

    @ManyToOne
    @JoinColumn(name = "empId")
    private EmployeeEntity employeeEntity;

    @ManyToOne
    @JoinColumn(name = "postId")
    private PostEntity postEntity;

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    public PostEntity getPostEntity() {
        return postEntity;
    }

    public void setPostEntity(PostEntity postEntity) {
        this.postEntity = postEntity;
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o)
//            return true;
//        if (o == null || getClass() != o.getClass())
//            return false;
//
//        LikesEntity that = (LikesEntity) o;
//
//        if (likeId != that.likeId)
//            return false;
//        if (!employeeEntity.equals(that.employeeEntity))
//            return false;
//
//        return postEntity.equals(that.postEntity);
//    }

//    @Override
//    public int hashCode() {
//        int result = likeId;
//
//        result = 31 * result + employeeEntity.hashCode();
//        result = 31 * result + postEntity.hashCode();
//
//        return result;
//    }


    @Override
    public String toString() {
        return "LikeEntity{" +
                "likeId=" + likeId +
                ", employeeEntity=" + employeeEntity +
                ", postEntity=" + postEntity +
                '}';
    }
}
