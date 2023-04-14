package com.nemirovsky.telebot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nemirovsky.telebot.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByEventId(long id);
}
