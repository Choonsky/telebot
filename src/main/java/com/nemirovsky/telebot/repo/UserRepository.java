package com.nemirovsky.telebot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nemirovsky.telebot.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);
}
