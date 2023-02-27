package com.sghfeedbacksystem.sghfeedbacksystem;

import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackTeam;
import com.sghfeedbacksystem.sghfeedbacksystem.model.Staff;
import com.sghfeedbacksystem.sghfeedbacksystem.model.User;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackTeamRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.StaffRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.UserRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.FeedbackRoleEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SghfeedbacksystemApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext configurableApplicationContext =
				SpringApplication.run(SghfeedbacksystemApplication.class, args);

		UserRepository staffRepository =
				configurableApplicationContext.getBean(StaffRepository.class);

		UserRepository feedbackTeamRepository =
				configurableApplicationContext.getBean(FeedbackTeamRepository.class);

		User staff =
				new Staff("sach_ok", "sachin", "ajayan", "sachin@gmail.com", "sachinlovesmen69", "assistant nurse", "Nursing");

		User feedbackTeam = new FeedbackTeam("kaiyuuuu", "chong", "kaiyu", "kaiyu@hotmail.com", "menlovekaiyu96", "Team Lead", FeedbackRoleEnum.PROCESSOWNER);

		staffRepository.save(staff);
		feedbackTeamRepository.save(feedbackTeam);

		staffRepository.delete(staff);


	}

}
