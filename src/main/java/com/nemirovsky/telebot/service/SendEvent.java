package com.nemirovsky.telebot.service;

import com.nemirovsky.telebot.DAO.EventCacheDAO;
import com.nemirovsky.telebot.config.ApplicationContextProvider;
import com.nemirovsky.telebot.model.TelegramBot;
import lombok.Setter;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Setter
//thread with event
public class SendEvent extends Thread {


    private long EventCacheId;
    private SendMessage sendMessage;

    public SendEvent() {
    }

    @SneakyThrows
    @Override
    public void run() {
        TelegramBot telegramBot = ApplicationContextProvider.getApplicationContext().getBean(TelegramBot.class);
        EventCacheDAO eventCacheDAO = ApplicationContextProvider.getApplicationContext().getBean(EventCacheDAO.class);
        telegramBot.execute(sendMessage);
        //if event it worked, need to remove it from the database of unresolved events
        eventCacheDAO.delete(EventCacheId);
    }
}
