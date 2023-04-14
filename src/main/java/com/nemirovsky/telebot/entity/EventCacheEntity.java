package com.nemirovsky.telebot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "event_cache")
@Getter
@Setter
//serves to save unhandled events after rebooting heroku
public class EventCacheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "id", columnDefinition = "serial")
    private long id;

    @Column(name = "time")
    private Date date;

    @Column(name = "description")
    private String description;

    @Column(name = "user_id")
    private long userId;

    public EventCacheEntity() {
    }

    public static EventCacheEntity eventTo(Date date, String description, long userId) {
        EventCacheEntity eventCacheEntity = new EventCacheEntity();
        eventCacheEntity.setDate(date);
        eventCacheEntity.setDescription(description);
        eventCacheEntity.setUserId(userId);
        return eventCacheEntity;
    }
}
