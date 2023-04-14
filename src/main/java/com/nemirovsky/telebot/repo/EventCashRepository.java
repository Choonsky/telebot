package com.nemirovsky.telebot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nemirovsky.telebot.entity.EventCashEntity;

public interface EventCashRepository extends JpaRepository<EventCashEntity, Long> {
    EventCashEntity findById(long id);
}
