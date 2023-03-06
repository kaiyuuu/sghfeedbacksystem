package com.sghfeedbacksystem.sghfeedbacksystem.data;

import com.sghfeedbacksystem.sghfeedbacksystem.model.*;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.*;
import com.sghfeedbacksystem.sghfeedbacksystem.service.FeedbackCategoryService;
import com.sghfeedbacksystem.sghfeedbacksystem.service.FeedbackSubCategoryService;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.FeedbackRoleEnum;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.FeedbackCategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component("loader")
public class DataLoader implements CommandLineRunner {


    @Autowired
    private FeedbackSubCategoryRepository feedbackSubCategoryRepository;
    @Autowired
    private FeedbackCategoryService feedbackCategoryService;
    @Autowired
    private FeedbackSubCategoryService feedbackSubCategoryService;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private FeedbackTeamRepository feedbackTeamRepository;

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
            loadStaff();
        }
    }

    public void loadFeedbackSubCategories() {
        System.out.println("loading feedback subcategories...");

        FeedbackSubCategory room = new FeedbackSubCategory("Room", "heres the description...");
        FeedbackSubCategory restrooms = new FeedbackSubCategory("Restroms", "heres the description...");
        FeedbackSubCategory staff = new FeedbackSubCategory("Staff", "heres the description...");
        FeedbackSubCategory pantry = new FeedbackSubCategory("Pantry", "heres the description...");
        FeedbackSubCategory bins = new FeedbackSubCategory("Bins", "heres the description...");

        FeedbackSubCategory biomedical = new FeedbackSubCategory("Biomedical", "heres the description...");
        FeedbackSubCategory hardware = new FeedbackSubCategory("Hardware", "heres the description...");
        FeedbackSubCategory plumbingSanitation = new FeedbackSubCategory("Plumbing and Sanitation", "heres the description...");

        FeedbackSubCategory staffBenefits = new FeedbackSubCategory("Staff Benefits", "heres the description...");
        FeedbackSubCategory harrassment = new FeedbackSubCategory("Harrassment & Abuse", "heres the description...");
        FeedbackSubCategory bullying = new FeedbackSubCategory("Bullying", "heres the description...");
        FeedbackSubCategory workArrangements = new FeedbackSubCategory("Work Arrangements", "heres the description...");
        FeedbackSubCategory unfairPractices = new FeedbackSubCategory("Unfair Practices", "heres the description...");
        FeedbackSubCategory workplaceCulture = new FeedbackSubCategory("Workplace Culture", "heres the description...");

        try {
            feedbackSubCategoryService.saveFeedbackSubCategory(room, new Long(1));
            feedbackSubCategoryService.saveFeedbackSubCategory(restrooms, new Long(1));
            feedbackSubCategoryService.saveFeedbackSubCategory(staff, new Long(1));
            feedbackSubCategoryService.saveFeedbackSubCategory(pantry, new Long(1));
            feedbackSubCategoryService.saveFeedbackSubCategory(bins, new Long(1));

            feedbackSubCategoryService.saveFeedbackSubCategory(biomedical, new Long(2));
            feedbackSubCategoryService.saveFeedbackSubCategory(hardware, new Long(2));
            feedbackSubCategoryService.saveFeedbackSubCategory(plumbingSanitation, new Long(2));

            feedbackSubCategoryService.saveFeedbackSubCategory(staffBenefits, new Long(3));
            feedbackSubCategoryService.saveFeedbackSubCategory(harrassment, new Long(3));
            feedbackSubCategoryService.saveFeedbackSubCategory(bullying, new Long(3));
            feedbackSubCategoryService.saveFeedbackSubCategory(workArrangements, new Long(3));
            feedbackSubCategoryService.saveFeedbackSubCategory(unfairPractices, new Long(3));
            feedbackSubCategoryService.saveFeedbackSubCategory(workplaceCulture, new Long(3));

        } catch (FeedbackCategoryNotFoundException exception) {
            System.out.println("sth went wrong in loading feedback subcategories");
        }

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

    public void loadStaff() {
        FeedbackSubCategory department1 = feedbackSubCategoryRepository.getReferenceById(1L);
        FeedbackSubCategory department2 = feedbackSubCategoryRepository.getReferenceById(2L);
        FeedbackSubCategory department3 = feedbackSubCategoryRepository.getReferenceById(3L);


        Staff staff1 = new Staff(new String("garyOng"),new String("gary"),new String("ong"),
                new String("gary.ong.b.k@sgh.com.sg"), new String("password"),new String("Assistance Director"),
                new String("Wel-Being and Division of Medicine"));

        Staff staff2 = new Staff(new String("euniceTan"),new String("eunice"),new String("ong"),
                new String("eunice.tan.h.y@sgh.com.sg"), new String("password"),new String("Manager"),
                "Wel-Being and Division of Medicine");

        Staff staff3 = new Staff(new String("aachinSajayan"),new String("sachin"),new String("ajayan"),
                new String("sachin.ajayan@gmail.com"), new String("password"),new String("Scrum Master"),
                "IT");
        User feedbackTeam = new FeedbackTeam("kaiyuuuu", "chong", "kaiyu", "kaiyu@hotmail.com", "menlovekaiyu96", "Team Lead", FeedbackRoleEnum.PROCESSOWNER);

        staffRepository.save(staff1);
        staffRepository.save(staff2);
        staffRepository.save(staff3);
        feedbackTeamRepository.save(feedbackTeam);

    }
}
