package com.nemirovsky.telebot.cache;

import com.nemirovsky.telebot.entity.Event;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Setter
@Getter
// used to save entered event data per session
public class EventCache {

    private final Map<Long, Event> eventMap = new HashMap<>();

    public void saveEventCache(long userId, Event event) {
        eventMap.put(userId, event);
    }
}
