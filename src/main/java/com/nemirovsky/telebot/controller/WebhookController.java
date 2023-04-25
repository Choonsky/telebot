package com.nemirovsky.telebot.controller;

import com.nemirovsky.telebot.model.TelegramBot;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Enumeration;

@RestController
public class WebhookController {

    private final TelegramBot telegramBot;

    public WebhookController(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

// point for message
    @PostMapping("/")
    public BotApiMethod<?> nUpdateReceived(@RequestBody Update update) {

        //TODO: del
        System.out.println("Incoming POST request: " + update);

        Message message = update.getMessage();
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("И что же вы, юзер " + userId + ", имеете в виду под \"" + message.getText() + "\"?");
        return sendMessage;

        //return telegramBot.onWebhookUpdateReceived(update);
    }

    @GetMapping("/")
    public String get(HttpServletRequest request) {

        System.out.println("Incoming GET request! ");

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.println(key + ": " + value);
        }

        return "<h1><center>This is Telebot v18 (AWS) testing center</center></h1>";
    }
}
