package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.User;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.UserRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.InvalidLoginCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        user = userRepository.save(user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User updateUser(User user) throws InvalidLoginCredentialsException {
        User updateUser = userRepository.findById(user.getUserId()).get();
        if(updateUser.getUsername().equals(user.getUsername()) && updateUser.getPassword().equals(user.getPassword())) {
            updateUser.setEmail(user.getEmail());
            updateUser.setFirstName(user.getFirstName());
            updateUser.setLastName(user.getLastName());
            updateUser.setPosition(user.getPosition());

            userRepository.save(updateUser);
            return updateUser;
        }
        throw new InvalidLoginCredentialsException("Could not update as different password");
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return user;
    }





}
