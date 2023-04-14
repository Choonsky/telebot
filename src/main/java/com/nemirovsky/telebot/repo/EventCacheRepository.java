package com.nemirovsky.telebot.repo;

import com.nemirovsky.telebot.entity.EventCacheEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCacheRepository extends JpaRepository<EventCacheEntity, Long> {
    EventCacheEntity findById(long id);
}
