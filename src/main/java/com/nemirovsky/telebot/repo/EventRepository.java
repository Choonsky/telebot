package com.nemirovsky.telebot.repo;

import com.nemirovsky.telebot.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByEventId(long id);
}
