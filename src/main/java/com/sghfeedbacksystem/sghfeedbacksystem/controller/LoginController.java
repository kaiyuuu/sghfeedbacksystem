package com.sghfeedbacksystem.sghfeedbacksystem.controller;

import com.sghfeedbacksystem.sghfeedbacksystem.dto.LoggedInUserDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.dto.LoggedInUserRequestDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.model.User;
import com.sghfeedbacksystem.sghfeedbacksystem.service.LoginService;
import com.sghfeedbacksystem.sghfeedbacksystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    //can work on it further if merger with FE is a problem
    @PostMapping("/login")
    public ResponseEntity<LoggedInUserDTO> login(@RequestBody LoggedInUserRequestDTO loggedInUserRequestDTO) throws Exception {
        try {
            LoggedInUserDTO loggedInUserDTO = loginService.login(loggedInUserRequestDTO);
            return new ResponseEntity<>(loggedInUserDTO, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
