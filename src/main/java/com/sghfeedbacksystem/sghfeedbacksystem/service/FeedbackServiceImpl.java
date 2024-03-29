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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public List<Feedback> findFeedbackBySubCategory(Long subCategoryId) {
        return feedbackRepository.findFeedbackBySubCategoryId(subCategoryId);
    }

    @Override
    public Feedback updateFeedbackSubcategory(Long feedbackId, Long subCategoryId) {
        Feedback updatedFeedback = feedbackRepository.findById(feedbackId).get();
        updatedFeedback.setFeedbackSubCategory(feedbackSubCategoryRepository.findById(subCategoryId).get());
        return feedbackRepository.save(updatedFeedback);
    }

    @Override
    public List<Feedback> findFeedbackByAuthor(Long userId) throws UserNotFoundException {

        Staff staff = (Staff) staffRepository.findById(userId).get();
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
    public Feedback findFeedbackById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId).get();
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


    @Override
    public Map<String, Integer> getStatusOfFeedbacks(List<Feedback> feedbacks) {

        Map<String, Integer> map = new HashMap<>();
        map.put("submitted", 0);
        map.put("reviewing", 0);
        map.put("closed", 0);
        for(Feedback f : feedbacks) {
            if(f.getFeedbackStatus().equals(FeedbackStatusEnum.SUBMITTED)) {
                int newCount = map.get("submitted") + 1;
                map.replace("submitted", newCount);
            } else if(f.getFeedbackStatus().equals(FeedbackStatusEnum.REVIEWING)) {
                int newCount = map.get("reviewing") + 1;
                map.replace("reviewing", newCount);
            } else {
                int newCount = map.get("closed") + 1;
                map.replace("closed", newCount);
            }
        }
        return map;
    }
}
