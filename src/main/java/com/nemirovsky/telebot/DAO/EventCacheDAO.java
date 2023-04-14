package com.nemirovsky.telebot.DAO;

import com.nemirovsky.telebot.entity.EventCacheEntity;
import com.nemirovsky.telebot.repo.EventCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//handles events not dispatched after reboot heroku
public class EventCacheDAO {

    private EventCacheRepository eventCacheRepository;

    @Autowired
    public void setEventCacheRepository(EventCacheRepository eventCacheRepository) {
        this.eventCacheRepository = eventCacheRepository;
    }

    public List<EventCacheEntity> findAllEventCache() {
        return eventCacheRepository.findAll();
    }

    public void save(EventCacheEntity eventCacheEntity) {
        eventCacheRepository.save(eventCacheEntity);
    }

    public void delete(long id) {
        eventCacheRepository.deleteById(id);
    }
}
