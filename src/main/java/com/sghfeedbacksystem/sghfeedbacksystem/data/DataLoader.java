package com.sghfeedbacksystem.sghfeedbacksystem.data;

import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.service.FeedbackCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component("loader")
public class DataLoader implements CommandLineRunner {

    @Autowired
    private FeedbackCategoryService feedbackCategoryService;

    public DataLoader() {
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("i <3 healthcare");
        loadData();
    }

    public void loadData() {

        if(feedbackCategoryService.findAllFeedbackCategory().isEmpty()) {
            loadFeedbackCategories();
            loadFeedbackSubCategories();
        }
    }

    public void loadFeedbackSubCategories() {
        System.out.println("loading feedback subcategories...");


    }

    public void loadFeedbackCategories() {

        System.out.println("loading feedback categories...");
        FeedbackCategory infrastructureAndAmenities = new FeedbackCategory("Infrastructure and Amenities", "heres the description ...");
        FeedbackCategory equipment = new FeedbackCategory("Equipment", "heres the description ...");
        FeedbackCategory hr = new FeedbackCategory("HR", "heres the description ...");
        FeedbackCategory it = new FeedbackCategory("IT", "heres the description ...");
        FeedbackCategory process = new FeedbackCategory("Process", "heres the description ...");
        FeedbackCategory newIdeas = new FeedbackCategory("New Ideas", "heres the description ...");
        FeedbackCategory others = new FeedbackCategory("Others", "heres the description ...");

        feedbackCategoryService.saveFeedbackCategory(infrastructureAndAmenities);
        feedbackCategoryService.saveFeedbackCategory(equipment);
        feedbackCategoryService.saveFeedbackCategory(hr);
        feedbackCategoryService.saveFeedbackCategory(it);
        feedbackCategoryService.saveFeedbackCategory(process);
        feedbackCategoryService.saveFeedbackCategory(newIdeas);
        feedbackCategoryService.saveFeedbackCategory(others);

    }
}
