package com.nemirovsky.telebot.service;

import lombok.Setter;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import com.nemirovsky.telebot.DAO.EventCashDAO;
import com.nemirovsky.telebot.config.ApplicationContextProvider;
import com.nemirovsky.telebot.model.TelegramBot;

@Setter
//thread with event
public class SendEvent extends Thread {


    private long eventCashId;
    private SendMessage sendMessage;

    public SendEvent() {
    }

    @SneakyThrows
    @Override
    public void run() {
        TelegramBot telegramBot = ApplicationContextProvider.getApplicationContext().getBean(TelegramBot.class);
        EventCashDAO eventCashDAO = ApplicationContextProvider.getApplicationContext().getBean(EventCashDAO.class);
        telegramBot.execute(sendMessage);
        //if event it worked, need to remove it from the database of unresolved events
        eventCashDAO.delete(eventCashId);
    }
}
