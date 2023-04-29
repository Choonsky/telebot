package com.nemirovsky.telebot.service;

import com.nemirovsky.telebot.DAO.EventCacheDAO;
import com.nemirovsky.telebot.model.EventCacheEntity;
import com.nemirovsky.telebot.telegram.TelegramBot;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import jakarta.annotation.PostConstruct;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

@Component
public class SendEventFromCache {

    private final EventCacheDAO eventCacheDAO;
    private final TelegramBot telegramBot;

    @Value("${telegrambot.adminId}")
    private int admin_id;

    @Autowired
    public SendEventFromCache(EventCacheDAO eventCacheDAO, TelegramBot telegramBot) {
        this.eventCacheDAO = eventCacheDAO;
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    @SneakyThrows
    //after every restart app  - check unspent events
    private void afterStart() {
        List<EventCacheEntity> list = eventCacheDAO.findAllEventCache();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(admin_id));
        sendMessage.setText("Произошла перезагрузка!");
        //telegramBot.execute(sendMessage);

        if (!list.isEmpty()) {
            for (EventCacheEntity eventCacheEntity : list) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(eventCacheEntity.getDate());
                SendEvent sendEvent = new SendEvent();
                sendEvent.setSendMessage(new SendMessage(String.valueOf(eventCacheEntity.getUserId()), eventCacheEntity.getDescription()));
                sendEvent.setEventCacheId(eventCacheEntity.getId());
                new Timer().schedule(new SimpleTask(sendEvent), calendar.getTime());
            }
        }
    }
}
