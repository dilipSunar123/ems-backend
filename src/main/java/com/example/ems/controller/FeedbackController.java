package com.example.ems.controller;

import com.example.ems.entity.FeedbackEntity;
import com.example.ems.repository.FeedbackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class FeedbackController {

    @Autowired
    private FeedbackRepo repo;

    @GetMapping("/getAllFeedbacks")
    private List<FeedbackEntity> getAllFeedbacks() {
        return repo.findAll();
    }

    @PostMapping("/addFeedback")
    private ResponseEntity<?> addFeedback(@RequestBody FeedbackEntity feedbackEntity) {
        repo.save(feedbackEntity);

        return ResponseEntity.ok("feedback added");
    }

    @GetMapping("/findFeedbackByEmpId/{empId}")
    private List<FeedbackEntity> findFeedbackByEmpId(@PathVariable int empId) {
        return repo.findByEmployeeEntityEmpId(empId);
    }

//    @PutMapping("/editFeedback/{feedbackId}/{feedback}/{rating}/{task}/{empId}")
//    private ResponseEntity<?> editFeedback(@PathVariable int feedbackId, @PathVariable String ) {
//
////        FeedbackEntity entity = repo.getReferenceById(feedbackId);
////
////        entity.setFeedbackId(feedbackEntity.getFeedbackId());
////        entity.setFeedback(feedbackEntity.getFeedback());
////        entity.setRating(feedbackEntity.getRating());
////        entity.setTask(feedbackEntity.getTask());
////        entity.setEmployeeEntity(feedbackEntity.getEmployeeEntity());
//
//        repo.save(feedbackEntity);
//
//        return ResponseEntity.ok("feedback updated");
//    }

    @DeleteMapping("/removeFeedback/{feedbackId}")
    private ResponseEntity<?> removeFeedback (@PathVariable int feedbackId) {
        repo.deleteById(feedbackId);

        return ResponseEntity.ok("feedback removed");
    }

}
