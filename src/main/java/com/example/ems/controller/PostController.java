package com.example.ems.controller;

import com.example.ems.entity.CommentsEntity;
import com.example.ems.entity.LikesEntity;
import com.example.ems.entity.PostEntity;
import com.example.ems.repository.CommentsRepo;
import com.example.ems.repository.LikesRepo;
import com.example.ems.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class PostController {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private LikesRepo likesRepo;

    @Autowired
    private CommentsRepo commentsRepo;

    @GetMapping("findAllPosts")
    private List<PostEntity> findAllPosts () {
        return postRepo.findAll();
    }

    @PostMapping("/addPost")
    private ResponseEntity<?> addPost (@RequestBody PostEntity newPostEntity) {

        postRepo.save(newPostEntity);

        return ResponseEntity.ok("Post added successfully!");

    }

    @PutMapping("/updatePost/{postId}/{postContent}")
    private ResponseEntity<?> updatePost(@PathVariable int postId, @PathVariable String postContent) {

        PostEntity entity = postRepo.getReferenceById(postId);

        entity.setPostId(entity.getPostId());
        entity.setEmployeeEntity(entity.getEmployeeEntity());
        entity.setPost(postContent);

        postRepo.save(entity);

        return ResponseEntity.ok("Post Updated successfully");

    }

    @GetMapping("/findPostByEmployeeId/{empId}")
    private List<PostEntity> findPostByEmployeeId(@PathVariable int empId) {
        return postRepo.findByEmployeeEntityEmpId(empId);
    }

// cannot be deleted directly as "likes" table references the post_id
//    @DeleteMapping("/deletePost/{postId}")
//    private ResponseEntity<?> deletePost(@PathVariable int postId) {
//        postRepo.deleteById(postId);
//
//        return ResponseEntity.ok("Post deleted successfully");
//    }

    @DeleteMapping("/deletePost/{postId}")
    private ResponseEntity<?> deletePost(@PathVariable int postId) {
        Optional<PostEntity> postOptional = postRepo.findById(postId);

        if (postOptional.isPresent()) {
            PostEntity post = postOptional.get();

            // Find all likes associated with the post
            List<LikesEntity> associatedLikes = likesRepo.findByPostEntity(post);
            List<CommentsEntity> associatedComments = commentsRepo.findByPostEntity(post);

            // Delete all associated likes
            likesRepo.deleteAll(associatedLikes);

            // Delete all associated comments
            commentsRepo.deleteAll(associatedComments);

            // Now delete the post
            postRepo.delete(post);

            return ResponseEntity.ok("Post and associated likes deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
