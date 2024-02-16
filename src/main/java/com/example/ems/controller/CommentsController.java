package com.example.ems.controller;

import com.example.ems.entity.CommentsEntity;
import com.example.ems.entity.PostEntity;
import com.example.ems.repository.CommentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CommentsController {

    @Autowired
    private CommentsRepo commentsRepo;

    @GetMapping("/findCommentsByPostId/{postId}")
    private List<CommentsEntity> findCommentsByPostId(@PathVariable int postId) {
        return commentsRepo.findByPostEntityPostId(postId);
    }

    @PostMapping("/addComment")
    private ResponseEntity<?> addComment(@RequestBody CommentsEntity entity) {
        commentsRepo.save(entity);

        return ResponseEntity.ok("Comment added successfully");
    }

    @PutMapping("/updateComment/{commentId}/{updatedComment}")
    private ResponseEntity<?> updateComment(@PathVariable int commentId, @PathVariable String updatedComment) {

        CommentsEntity entity = commentsRepo.getReferenceById(commentId);

        CommentsEntity newCommentEntity = new CommentsEntity();

        newCommentEntity.setCommentId(entity.getCommentId());
        newCommentEntity.setEmployeeEntity(entity.getEmployeeEntity());
        newCommentEntity.setPostEntity(entity.getPostEntity());
        newCommentEntity.setComment(updatedComment);    // updated data

        commentsRepo.save(newCommentEntity);

        return ResponseEntity.ok("Comment Updated!");

    }

    @DeleteMapping("/deleteComment/{commentId}")
    private ResponseEntity<?> deleteComment(@PathVariable int commentId) {
        commentsRepo.deleteById(commentId);

        return ResponseEntity.ok("Comment deleted successfully");
    }

}
