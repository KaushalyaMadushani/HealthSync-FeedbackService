package com.healthsync.feedback.repository;

import com.healthsync.feedback.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {
    List<Feedback> findByPatientId(String patientId);
}
