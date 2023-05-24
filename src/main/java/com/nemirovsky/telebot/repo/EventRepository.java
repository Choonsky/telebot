package com.nemirovsky.telebot.repo;

import com.nemirovsky.telebot.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByEventId(long id);
}
