package com.example.ems.repository;

import com.example.ems.entity.CommentsEntity;
import com.example.ems.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepo extends JpaRepository<CommentsEntity, Integer> {

    List<CommentsEntity> findByPostEntityPostId(int postId);

    List<CommentsEntity> findByPostEntity(PostEntity postEntity);

}
