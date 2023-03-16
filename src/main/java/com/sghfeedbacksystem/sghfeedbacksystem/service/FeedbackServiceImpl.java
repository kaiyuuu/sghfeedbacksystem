package com.sghfeedbacksystem.sghfeedbacksystem.service;
import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackSubCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.model.Staff;
import com.sghfeedbacksystem.sghfeedbacksystem.model.User;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackSubCategoryRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.StaffRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.FeedbackStatusEnum;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.*;
import jdk.internal.net.http.common.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService{

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private FeedbackSubCategoryRepository feedbackSubCategoryRepository;
    @Autowired
    private StaffRepository staffRepository;


    @Override
    public List<Feedback> findAllFeedbackByDate(LocalDateTime startDate, LocalDateTime endDate) {

        List<Feedback> allFeedback = feedbackRepository.findAll();
        return allFeedback.stream().filter(x ->
                x.getFeedbackDate().isAfter(startDate)
                        && x.getFeedbackDate().isBefore(endDate)).collect(Collectors.toList());

    }

    @Override
    public List<Feedback> findFeedbackByCategory(Long categoryId) {
        return feedbackRepository.findFeedbackByCategoryId(categoryId);
    }

    //not tested
    @Override
    public List<Feedback> findFeedbackBySubCategory(Long subCategoryId) {
        return feedbackRepository.findFeedbackBySubCategoryId(subCategoryId);
    }

    @Override
    public List<Feedback> findFeedbackByAuthor(Long userId) throws UserNotFoundException {

        if(staffRepository.findById(userId).get() == null) {
            throw new UserNotFoundException();
        }

        return feedbackRepository.findFeedbackByFeedbackAuthorId(userId);
    }

    //not tested
    @Override
    public List<Feedback> findFeedbacksUnderReview() {
        List<Feedback> allFeedback = feedbackRepository.findAll();
        return allFeedback.stream().filter(x ->
                x.getFeedbackStatus().equals(FeedbackStatusEnum.REVIEWING))
                .collect(Collectors.toList());

    }

    //not tested
    @Override
    public List<Feedback> findFeedbacksSubmitted() {
        List<Feedback> allFeedback = feedbackRepository.findAll();
        return allFeedback.stream().filter(x ->
                x.getFeedbackStatus().equals(FeedbackStatusEnum.SUBMITTED))
                .collect(Collectors.toList());
    }

    @Override
    public List<Feedback> findFeedbacksPublished() {
        List<Feedback> allFeedback = feedbackRepository.findAll();
        return allFeedback.stream().filter(x -> x.getPublished())
                .collect(Collectors.toList());
    }

    @Override
    public Feedback saveFeedback(Staff staff, Feedback feedback, FeedbackSubCategory feedbackSubCategory) throws StaffNotFoundException, FeedbackCategoryNotFoundException{

        Optional<User> managedStaff = staffRepository.findById(staff.getUserId());
        Optional<FeedbackSubCategory> managedFeedbackSubCategory = feedbackSubCategoryRepository.findById(feedbackSubCategory.getSubCategoryId());
        if(managedStaff.get() == null) {
            throw new StaffNotFoundException();
        } else if(managedFeedbackSubCategory.get() == null) {
            throw new FeedbackCategoryNotFoundException();
        } else {
            feedback.setFeedbackAuthor((Staff)managedStaff.get());
            feedback.setFeedbackSubCategory(feedbackSubCategory);
            feedback.setFeedbackStatus(FeedbackStatusEnum.SUBMITTED);
            return feedbackRepository.save(feedback);
        }
    }

    @Override
    public Long deleteFeedback(Long feedbackId) throws FeedbackNotFoundException, CannotDeleteFeedbackUnderReviewException {

        if(feedbackRepository.findById(feedbackId).get() == null) {
            throw new FeedbackNotFoundException();
        } else {
            Feedback feedbackToDelete = feedbackRepository.findById(feedbackId).get();
            if(!feedbackToDelete.getFeedbackStatus().equals(FeedbackStatusEnum.SUBMITTED)) {
                throw new CannotDeleteFeedbackUnderReviewException();
            } else {
                feedbackRepository.delete(feedbackToDelete);
                System.out.println("feedbackServiceImpl :: delete feedback :: successfully Deleted feedback: " + feedbackId);
                return feedbackId;
            }
        }
    }

    //not tested
    @Override
    public Feedback updateFeedbackStatus(Long feedbackId, FeedbackStatusEnum feedbackStatus) throws FeedbackNotFoundException {

        Feedback feedbackToUpdate = feedbackRepository.findById(feedbackId).get();
        if(feedbackToUpdate!=null) {
            feedbackToUpdate.setFeedbackStatus(feedbackStatus);
            return feedbackRepository.save(feedbackToUpdate);
        } else {
            throw new FeedbackNotFoundException();
        }
    }

    @Override
    public Feedback publishFeedback(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId).get();
        feedback.setPublished(true);
        return feedbackRepository.save(feedback);
    }
}
