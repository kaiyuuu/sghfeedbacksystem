package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.dto.LoggedInUserDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.dto.LoggedInUserRequestDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.model.User;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.UserRoleEnum;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.InvalidLoginCredentialsException;
import com.sghfeedbacksystem.sghfeedbacksystem.util.logger.LoggingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

    private Logger logger = LoggerFactory.getLogger(LoggingController.class);

    private UserService userService;

    public LoginServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public LoggedInUserDTO login(LoggedInUserRequestDTO loggedInUserRequestDTO) throws InvalidLoginCredentialsException {
        LoggedInUserDTO retrievedUser = findUserByUsername(loggedInUserRequestDTO.getUsername());
        if (retrievedUser.getPassword().equals(loggedInUserRequestDTO.getPassword())) {
            return retrievedUser;
        } else {
            throw new InvalidLoginCredentialsException("Invalid username or password!");
        }
    }

    @Override
    public LoggedInUserDTO findUserByUsername(String username) {
        LoggedInUserDTO retrievedUser = new LoggedInUserDTO();
        if (userService.findUserByUsername(username) != null) {
            User user = userService.findUserByUsername(username);
            String userEnum = "";
            if (retrievedUser.getUserEnum().equals(UserRoleEnum.STAFF)) {
                userEnum = "STAFF";
            } else if (retrievedUser.getUserEnum().equals(UserRoleEnum.ADMIN)) {
                userEnum = "ADMIN";
            } else if (retrievedUser.getUserEnum().equals(UserRoleEnum.PROCESSOWNER)) {
                userEnum = "PROCESSOWNER";
            }

            retrievedUser = new LoggedInUserDTO(
                    retrievedUser.getUserId(),
                    retrievedUser.getUsername(),
                    retrievedUser.getFirstName(),
                    retrievedUser.getLastName(),
                    retrievedUser.getEmail(),
                    retrievedUser.getPassword(),
                    userEnum);
        }
        return retrievedUser;
    }
}
