package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.User;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.UserRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.InvalidLoginCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String username, String password) throws InvalidLoginCredentialsException {
        User user = userRepository.findUserByUsername(username);
        if(user == null || !(user.getPassword().equals(password))) {
            throw new InvalidLoginCredentialsException();
        } else {
            return user;
        }
    }
}
