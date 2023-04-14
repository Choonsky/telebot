package com.nemirovsky.telebot.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import com.nemirovsky.telebot.entity.Event;

import java.util.HashMap;
import java.util.Map;

@Service
@Setter
@Getter
// used to save entered event data per session
public class EventCache {

    private final Map<Long, Event> eventMap = new HashMap<>();

    public void saveEventCash(long userId, Event event) {
        eventMap.put(userId, event);
    }
}
