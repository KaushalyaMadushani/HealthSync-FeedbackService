package com.healthsync.feedback;

import com.healthsync.feedback.model.Feedback;
import com.healthsync.feedback.repository.FeedbackRepository;
import com.healthsync.feedback.service.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FeedbackServiceTest {
    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateFeedback() {
        Feedback feedback = Feedback.builder()
                .patientId("1")
                .comment("Great service!")
                .rating(5)
                .createdAt(LocalDateTime.now())
                .build();

        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

        Feedback createdFeedback = feedbackService.createFeedback(feedback);

        assertNotNull(createdFeedback);
        assertEquals("Great service!", createdFeedback.getComment());
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }

    @Test
    public void testGetFeedbackById() {
        Feedback feedback = Feedback.builder()
                .feedbackId("1")
                .patientId("1")
                .comment("Great service!")
                .rating(5)
                .createdAt(LocalDateTime.now())
                .build();

        when(feedbackRepository.findById("1")).thenReturn(Optional.of(feedback));

        Optional<Feedback> foundFeedback = feedbackService.getFeedbackById("1");

        assertTrue(foundFeedback.isPresent());
        assertEquals("Great service!", foundFeedback.get().getComment());
        verify(feedbackRepository, times(1)).findById("1");
    }

    @Test
    public void testGetAllFeedbacks() {
        Feedback feedback1 = Feedback.builder().comment("Good experience").rating(4).build();
        Feedback feedback2 = Feedback.builder().comment("Not satisfied").rating(2).build();
        List<Feedback> feedbacks = Arrays.asList(feedback1, feedback2);

        when(feedbackRepository.findAll()).thenReturn(feedbacks);

        List<Feedback> allFeedbacks = feedbackService.getAllFeedbacks();

        assertEquals(2, allFeedbacks.size());
        verify(feedbackRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateFeedback() {
        Feedback existingFeedback = Feedback.builder()
                .feedbackId("1")
                .patientId("1")
                .comment("Good service")
                .rating(4)
                .createdAt(LocalDateTime.now())
                .build();

        Feedback updatedFeedback = Feedback.builder()
                .feedbackId("1")
                .patientId("1")
                .comment("Excellent service")
                .rating(5)
                .createdAt(LocalDateTime.now())
                .build();

        when(feedbackRepository.findById("1")).thenReturn(Optional.of(existingFeedback));
        when(feedbackRepository.save(any(Feedback.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Feedback result = feedbackService.updateFeedback("1", updatedFeedback);

        assertNotNull(result);
        assertEquals("Excellent service", result.getComment());
        assertEquals(5, result.getRating());
        verify(feedbackRepository, times(1)).save(existingFeedback);
    }

    @Test
    public void testDeleteFeedback() {
        doNothing().when(feedbackRepository).deleteById("1");

        feedbackService.deleteFeedback("1");

        verify(feedbackRepository, times(1)).deleteById("1");
    }
}
