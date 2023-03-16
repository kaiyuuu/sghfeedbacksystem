package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.User;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.InvalidLoginCredentialsException;

import java.util.List;

public interface UserService {

    public User saveUser(User user);

    public List<User> getAllUsers();

    public User updateUser(User user) throws InvalidLoginCredentialsException;

    public User findUserByUsername(String username);

    public User findUserById(Long Id);



}
