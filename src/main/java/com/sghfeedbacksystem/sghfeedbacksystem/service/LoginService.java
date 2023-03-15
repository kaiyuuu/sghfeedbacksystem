package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.dto.LoggedInUserDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.dto.LoggedInUserRequestDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.InvalidLoginCredentialsException;

public interface LoginService {

    public LoggedInUserDTO login(LoggedInUserRequestDTO loggedInUserRequestDTO) throws InvalidLoginCredentialsException;
    public LoggedInUserDTO findUserByUsername(String username);
}
