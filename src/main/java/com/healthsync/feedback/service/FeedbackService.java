package com.healthsync.feedback.service;

import com.healthsync.feedback.model.Feedback;
import com.healthsync.feedback.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback createFeedback(Feedback feedback){
        feedback.setCreatedAt(LocalDateTime.now());
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedbacks(){
        return feedbackRepository.findAll();
    }

    public List<Feedback> getFeedbackByPatientId(String patientId){
        return feedbackRepository.findByPatientId(patientId);
    }

    public Optional<Feedback> getFeedbackById(String id){
        return feedbackRepository.findById(id);
    }

    public Feedback updateFeedback(String id, Feedback updatedFeedback){
        return feedbackRepository.findById(id)
                .map(feedback -> {
                    feedback.setComment(updatedFeedback.getComment());
                    feedback.setRating(updatedFeedback.getRating());
                    return feedbackRepository.save(feedback);

                })
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found with id: " + id));
    }

    public void deleteFeedback(String id){
        feedbackRepository.deleteById(id);
    }

}
