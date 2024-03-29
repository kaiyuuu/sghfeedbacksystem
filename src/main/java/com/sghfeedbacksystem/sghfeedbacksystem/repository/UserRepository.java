package com.sghfeedbacksystem.sghfeedbacksystem.repository;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.User;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.UserRoleEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


@NoRepositoryBean
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username=:username")
    User findUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.userRole=:userRole")
    List<User> findUsersByUserRole(UserRoleEnum userRole);
}
