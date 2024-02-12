package com.example.ems.repository;

import com.example.ems.entity.LikesEntity;
import com.example.ems.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepo extends JpaRepository<LikesEntity, Integer> {

    public List<LikesEntity> findByPostEntityPostId(int postId);

    public List<LikesEntity> findByPostEntity(PostEntity postEntity);

    public LikesEntity findByEmployeeEntityEmpIdAndPostEntityPostId(int empId, int postId);

}
