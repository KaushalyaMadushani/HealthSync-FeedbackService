package com.healthsync.feedback.controller;

import com.healthsync.feedback.model.Feedback;
import com.healthsync.feedback.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {


    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback){
        return ResponseEntity.ok(feedbackService.createFeedback(feedback));
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks(){
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Feedback>> getFeedbackByPatientId(@PathVariable String patientId){
            return ResponseEntity.ok(feedbackService.getFeedbackByPatientId(patientId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable String id){
        return feedbackService.getFeedbackById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable String id, @RequestBody Feedback updatedFeedback){
        return ResponseEntity.ok(feedbackService.updateFeedback(id, updatedFeedback));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFeedback(@PathVariable String id){
        feedbackService.deleteFeedback(id);
    }
}
