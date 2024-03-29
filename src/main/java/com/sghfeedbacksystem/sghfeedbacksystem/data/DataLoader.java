package com.sghfeedbacksystem.sghfeedbacksystem.data;

import com.sghfeedbacksystem.sghfeedbacksystem.dto.ResponseBodyPublishStatusDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.model.*;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.*;
import com.sghfeedbacksystem.sghfeedbacksystem.service.*;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.FeedbackStatusEnum;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.UserRoleEnum;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Component("loader")
public class DataLoader implements CommandLineRunner {


    @Autowired
    private FeedbackSubCategoryRepository feedbackSubCategoryRepository;
    @Autowired
    private FeedbackCategoryService feedbackCategoryService;
    @Autowired
    private FeedbackSubCategoryService feedbackSubCategoryService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private FeedbackResponseService feedbackResponseService;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private FeedbackTeamRepository feedbackTeamRepository;

    @Autowired
    private EmailService emailService;

    public DataLoader() {
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("i <3 healthcare");
//        emailService.dailyEmailUpdate();
        loadData();
    }
    /*
    //@Scheduled(fixedDelay = 100)
    //@Scheduled(cron = "0 23 14 * * *")
    public void runScheduler() {
        emailService.dailyEmailUpdate();
    }*/
    public void loadData() {

        if(feedbackCategoryService.findAllFeedbackCategory().isEmpty()) {
            loadFeedbackCategories();
            loadStaff();
            loadFeedbackSubCategories();
            loadFeedback();
        }
//        testServices();
    }

    public void loadFeedbackSubCategories() {
        System.out.println("loading feedback subcategories...");

        FeedbackSubCategory room = new FeedbackSubCategory("Room", "heres the description...");
        room.setFeedbackSubCategoryPo((FeedbackTeam) feedbackTeamRepository.findById(3L).get());
        FeedbackSubCategory restrooms = new FeedbackSubCategory("Restrooms", "heres the description...");
        FeedbackSubCategory staffPantry = new FeedbackSubCategory("Staff Pantry", "heres the description...");
        FeedbackSubCategory bins = new FeedbackSubCategory("Bins", "heres the description...");

        FeedbackSubCategory biomedical = new FeedbackSubCategory("Biomedical", "heres the description...");
        FeedbackSubCategory hardware = new FeedbackSubCategory("Hardware", "heres the description...");
        FeedbackSubCategory plumbingSanitation = new FeedbackSubCategory("Plumbing & Sanitation", "heres the description...");

        FeedbackSubCategory staffBenefits = new FeedbackSubCategory("Staff Benefits", "heres the description...");
        staffBenefits.setFeedbackSubCategoryPo((FeedbackTeam) feedbackTeamRepository.findById(2L).get());
        FeedbackSubCategory harrassment = new FeedbackSubCategory("Harassment & Abuse", "heres the description...");
        FeedbackSubCategory bullying = new FeedbackSubCategory("Bullying", "heres the description...");
        FeedbackSubCategory workArrangements = new FeedbackSubCategory("Work Arrangements", "heres the description...");
        FeedbackSubCategory unfairPractices = new FeedbackSubCategory("Unfair Practice", "heres the description...");
        FeedbackSubCategory workplaceCulture = new FeedbackSubCategory("Workplace Culture", "heres the description...");
        workplaceCulture.setFeedbackSubCategoryPo((FeedbackTeam) feedbackTeamRepository.findById(2L).get());

        FeedbackSubCategory software = new FeedbackSubCategory("Software", "heres the description...");
        FeedbackSubCategory hardwareIT = new FeedbackSubCategory("IT Hardware", "heres the description...");

        FeedbackSubCategory businessOffice = new FeedbackSubCategory("Business Office", "heres the description...");
        FeedbackSubCategory operatingTheatre = new FeedbackSubCategory("Operating Theatre", "heres the description...");
        FeedbackSubCategory outpatientClinics = new FeedbackSubCategory("Outpatient Clinics", "heres the description...");
        FeedbackSubCategory ILEC = new FeedbackSubCategory("ILEC", "heres the description...");
        FeedbackSubCategory AEC = new FeedbackSubCategory("AEC", "heres the description...");

        FeedbackSubCategory health = new FeedbackSubCategory("Health", "heres the description...");
        FeedbackSubCategory family = new FeedbackSubCategory("Family", "heres the description...");
        FeedbackSubCategory workTransformations = new FeedbackSubCategory("Work Transformations", "heres the description...");
        FeedbackSubCategory kindness = new FeedbackSubCategory("Kindness", "heres the description...");
        FeedbackSubCategory others = new FeedbackSubCategory("Others", "heres the description...");

        FeedbackSubCategory titleAndFreeText = new FeedbackSubCategory("Title and free text", "heres the description...");



        try {
            feedbackSubCategoryService.saveFeedbackSubCategory(room, 1L);
            feedbackSubCategoryService.saveFeedbackSubCategory(restrooms, 1L);
            feedbackSubCategoryService.saveFeedbackSubCategory(staffPantry, 1L);
            feedbackSubCategoryService.saveFeedbackSubCategory(bins, 1L);

            feedbackSubCategoryService.saveFeedbackSubCategory(biomedical,2L);
            feedbackSubCategoryService.saveFeedbackSubCategory(hardware, 2L);
            feedbackSubCategoryService.saveFeedbackSubCategory(plumbingSanitation, 2L);

            feedbackSubCategoryService.saveFeedbackSubCategory(staffBenefits, 3L);
            feedbackSubCategoryService.saveFeedbackSubCategory(harrassment, 3L);
            feedbackSubCategoryService.saveFeedbackSubCategory(bullying, 3L);
            feedbackSubCategoryService.saveFeedbackSubCategory(workArrangements,3L);
            feedbackSubCategoryService.saveFeedbackSubCategory(unfairPractices,3L);
            feedbackSubCategoryService.saveFeedbackSubCategory(workplaceCulture, 3L);

            feedbackSubCategoryService.saveFeedbackSubCategory(software, 4L);
            feedbackSubCategoryService.saveFeedbackSubCategory(hardwareIT, 4L);

            feedbackSubCategoryService.saveFeedbackSubCategory(businessOffice, 5L);
            feedbackSubCategoryService.saveFeedbackSubCategory(operatingTheatre, 5L);
            feedbackSubCategoryService.saveFeedbackSubCategory(outpatientClinics, 5L);
            feedbackSubCategoryService.saveFeedbackSubCategory(ILEC, 5L);
            feedbackSubCategoryService.saveFeedbackSubCategory(AEC, 5L);

            feedbackSubCategoryService.saveFeedbackSubCategory(health, 6L);
            feedbackSubCategoryService.saveFeedbackSubCategory(family, 6L);
            feedbackSubCategoryService.saveFeedbackSubCategory(workTransformations, 6L);
            feedbackSubCategoryService.saveFeedbackSubCategory(kindness, 6L);
            feedbackSubCategoryService.saveFeedbackSubCategory(others, 6L);

            feedbackSubCategoryService.saveFeedbackSubCategory(titleAndFreeText, 7L);
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
//        FeedbackSubCategory department1 = feedbackSubCategoryRepository.getReferenceById(1L);
//        FeedbackSubCategory department2 = feedbackSubCategoryRepository.getReferenceById(2L);
//        FeedbackSubCategory department3 = feedbackSubCategoryRepository.getReferenceById(3L);

//        FeedbackSubCategory restrooms = feedbackSubCategoryRepository.getReferenceById(2L);
//        FeedbackSubCategory bullying = feedbackSubCategoryRepository.getReferenceById(11L);


        Staff staff1 = new Staff(new String("sghstaff"),new String("sgh"),new String("staff"),
                new String("ong.b.k.gary@gmail.com"), new String("password"),new String("Assistance Director"),
                UserRoleEnum.STAFF);

        FeedbackTeam feedbackTeam1 = new FeedbackTeam("po1eunice", "eunice", "po1", "eunice.tan.h.y@sgh.com.sg", "password", "Team Lead", UserRoleEnum.PROCESSOWNER);

        FeedbackTeam feedbackTeam2 = new FeedbackTeam("po2gary", "gary", "po2", "gary.ong.b.k@sgh.com.sg", "password", "Head Accountant", UserRoleEnum.PROCESSOWNER);

        FeedbackTeam feedbackTeam3 = new FeedbackTeam("owbadmin", "owb", "admin", "sghowb@gmail.com", "password", "Admin", UserRoleEnum.ADMIN);


        staffRepository.save(staff1);
//        restrooms.setFeedbackSubCategoryPo( feedbackTeam1);
//        bullying.setFeedbackSubCategoryPo(feedbackTeam2);
        feedbackTeamRepository.save(feedbackTeam1);
        feedbackTeamRepository.save(feedbackTeam2);
        feedbackTeamRepository.save(feedbackTeam3);
//        feedbackSubCategoryRepository.save(restrooms);
//        feedbackSubCategoryRepository.save(bullying);



    }

    public void loadFeedback() {
        User staff1 = staffRepository.findById(1L).get();
        FeedbackSubCategory feedbackSubCategory1 = feedbackSubCategoryRepository.findById(1L).get();
        FeedbackSubCategory feedbackSubCategory2 = feedbackSubCategoryRepository.findById(8L).get();
        FeedbackSubCategory feedbackSubCategory3 = feedbackSubCategoryRepository.findById(13L).get();

        Feedback feedback1 = new Feedback(new String ("Room Feedback"),
                new String("Feedback about room in the facility"),
                Boolean.FALSE, LocalDateTime.now());
        Feedback feedback2 = new Feedback(new String ("Feedback on staff benefits"),
                new String("Feedback about staff benefits"),
                Boolean.TRUE, LocalDateTime.now());
        feedback2.setPublished(true);
        Feedback feedback3 = new Feedback(new String ("Workplace Culture"),
                new String("Workplace Culture feedback"),
                Boolean.TRUE, LocalDateTime.now());
        FeedbackResponse feedbackResponse1 = new FeedbackResponse("Noted", "Response to feedback", LocalDateTime.now());
        try {
            feedbackService.saveFeedback((Staff) staff1, feedback1, feedbackSubCategory1);
            feedbackService.saveFeedback((Staff) staff1, feedback2, feedbackSubCategory2);
            feedbackService.saveFeedback((Staff) staff1, feedback3, feedbackSubCategory3);

            feedbackResponseService.acceptFeedback(1L, new ResponseBodyPublishStatusDTO("Noted", true, ""));
        } catch(StaffNotFoundException | FeedbackCategoryNotFoundException exception) {
            System.out.println("something went wrong while loading feedback");
        }

//        try {
//            feedbackService.deleteFeedback(3L);
//        } catch (FeedbackNotFoundException | CannotDeleteFeedbackUnderReviewException exception) {
//            System.out.println("something went wrong while deleting feedback");
//        }
    }

    public void testServices() {

    LocalDateTime start = LocalDateTime.of(2015,
                Month.JULY, 29, 19, 30, 40);
    LocalDateTime end = LocalDateTime.now();
    List<Feedback> feebackByDate = feedbackService.findAllFeedbackByDate(start, end);
    List<Feedback> feebackByCategory = feedbackService.findFeedbackByCategory(1L);
    List<Feedback> feebackBySubCategory = feedbackService.findFeedbackBySubCategory(1L);
    List<Feedback> feedbackUnderReview = feedbackService.findFeedbacksUnderReview();
        System.out.println("feebackByDate ::");
        for(Feedback f : feebackByDate) {
        System.out.println(f.getFeedbackTitle());
        }
        System.out.println("feebackByCategory ::");
        for(Feedback f : feebackByCategory) {
            System.out.println(f.getFeedbackTitle());
        }
        System.out.println("feebackBySubCategory ::");
        for(Feedback f : feebackBySubCategory) {
            System.out.println(f.getFeedbackTitle());
        }
        System.out.println("feedbackUnderReview ::");
        for(Feedback f : feedbackUnderReview) {
            System.out.println(f.getFeedbackTitle());
        }
        try {
        List<Feedback> feedbackByAuthor = feedbackService.findFeedbackByAuthor(1L);
            System.out.println("feedbackByAuthor ::");
            for(Feedback f : feedbackByAuthor) {
                System.out.println(f.getFeedbackTitle());
            }

        } catch (UserNotFoundException exception) {
        System.out.println("somethign wenet wrong in finding feedback by author");
    }



    }
}
