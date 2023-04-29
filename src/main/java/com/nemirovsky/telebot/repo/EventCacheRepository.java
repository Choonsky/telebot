package com.nemirovsky.telebot.repo;

import com.nemirovsky.telebot.model.EventCacheEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCacheRepository extends JpaRepository<EventCacheEntity, Long> {
    EventCacheEntity findById(long id);
}
