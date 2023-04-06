package com.sghfeedbacksystem.sghfeedbacksystem.controller;

import com.sghfeedbacksystem.sghfeedbacksystem.dto.FeedbackDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.dto.StartEndDateDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.service.FeedbackCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adminAnalytics")
public class AdminAnalyticsController {

    @Autowired
    FeedbackCategoryService feedbackCategoryService;

    @PostMapping("/getTop3Categories")
    public ResponseEntity<List<Pair<FeedbackCategory, Integer>>> getTop3Categories(@RequestBody StartEndDateDTO startEndDateDTO) {
        try {
            LocalDateTime startDate = convertStringToDate(startEndDateDTO.getStartDateString());
            LocalDateTime endDate = convertStringToDate(startEndDateDTO.getEndDateString());
            List<Pair<FeedbackCategory, Integer>> list = feedbackCategoryService.findPopularFeedbackCategories(startDate, endDate);
            return new ResponseEntity<List<Pair<FeedbackCategory, Integer>>>(list, HttpStatus.OK);
        } catch (ParseException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/getAllCategoryCounts")
    public ResponseEntity<Map<String, Integer>> getAllCategoryCounts(@RequestBody StartEndDateDTO startEndDateDTO) {
        try {
            LocalDateTime startDate = convertStringToDate(startEndDateDTO.getStartDateString());
            LocalDateTime endDate = convertStringToDate(startEndDateDTO.getEndDateString());
            Map<FeedbackCategory, Integer> map = feedbackCategoryService.findFeedbackCategoryCounts(startDate, endDate);
            Map<String,Integer> newMap = convertFeedbackCategoryToString(map);
            return new ResponseEntity<Map<String, Integer>>(newMap, HttpStatus.OK);
        } catch (ParseException exception) {
            System.out.println("something went wrong with converting string");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    public LocalDateTime convertStringToDate(String dateString) throws ParseException {
        String datePattern = "EEE MMM dd yyyy HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        LocalDateTime date = LocalDateTime.parse(dateString, formatter);
        return date;
    }

    public Map<String,Integer> convertFeedbackCategoryToString(Map<FeedbackCategory, Integer> feedbackCategoryIntegerMap) {
        Map<String, Integer> map = new HashMap<>();
        for(FeedbackCategory f : feedbackCategoryIntegerMap.keySet()) {
            map.put(f.getFeedbackCategoryName(), feedbackCategoryIntegerMap.get(f));
        }
        return map;
    }
}
