package com.sghfeedbacksystem.sghfeedbacksystem.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component("loader")
public class DataLoader implements CommandLineRunner {

    public DataLoader() {
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("i <3 healthcare");
        loadData();
    }

    public void loadData() {
    }
}
