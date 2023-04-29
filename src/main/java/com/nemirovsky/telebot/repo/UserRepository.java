package com.nemirovsky.telebot.repo;

import com.nemirovsky.telebot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);
}
