package com.example.ems.controller;

import com.example.ems.entity.LikesEntity;
import com.example.ems.repository.LikesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LikesController {

    @Autowired
    private LikesRepo likesRepo;

    @PostMapping("/addLike")
    private ResponseEntity<?> addLike(@RequestBody LikesEntity likesEntity) {

        List<LikesEntity> entityList = likesRepo.findAll();

        for (LikesEntity entity: entityList) {
            if (likesEntity.getEmployeeEntity().getEmpId() == entity.getEmployeeEntity().getEmpId()) {
                if (likesEntity.getPostEntity().getPostId() == entity.getPostEntity().getPostId()) {
                    // post already liked by the emp

                    System.out.println(likesEntity.getEmployeeEntity());
                    System.out.println(likesEntity.getPostEntity());

                    System.out.println("Like ID : " + entity.getLikeId());
                    return ResponseEntity.ok("Post already liked. id - " + entity.getLikeId());
                }
            }
        }
        likesRepo.save(likesEntity);

        return ResponseEntity.ok("Like added!");
    }

    @DeleteMapping("/removeLike/{likeId}")
    private ResponseEntity<?> removeLike(@PathVariable int likeId) {
        likesRepo.deleteById(likeId);

        return ResponseEntity.ok("Like Removed");
    }

    @DeleteMapping("/removeLikeByEmpIdAndPostId/{empId}/{postId}")
    private ResponseEntity<?> removeLikeByEmpIdAndPostId (@PathVariable int empId, @PathVariable int postId) {

        LikesEntity entity = likesRepo.findByEmployeeEntityEmpIdAndPostEntityPostId(empId, postId);

        if (entity != null) {
            likesRepo.delete(entity);
            return ResponseEntity.ok("Like removed");
        }

        return ResponseEntity.internalServerError().body("Like does not exist in the db");

    }

    @GetMapping("/findLikesByPostId/{postId}")
    private List<LikesEntity> findLikesByPostId(@PathVariable int postId) {

        System.out.println("No of likes : " + likesRepo.findByPostEntityPostId(postId).size());

        return likesRepo.findByPostEntityPostId(postId);
    }

}
