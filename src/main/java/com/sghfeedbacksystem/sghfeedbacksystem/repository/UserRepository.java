package com.sghfeedbacksystem.sghfeedbacksystem.repository;

import com.sghfeedbacksystem.sghfeedbacksystem.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface UserRepository extends CrudRepository<User, Long> {

}
