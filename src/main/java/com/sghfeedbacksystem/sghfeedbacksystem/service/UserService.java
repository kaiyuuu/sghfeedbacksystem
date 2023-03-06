package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.User;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.InvalidLoginCredentialsException;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.UserNotFoundException;

public interface UserService {

    public User login(String username, String password) throws InvalidLoginCredentialsException;
}
