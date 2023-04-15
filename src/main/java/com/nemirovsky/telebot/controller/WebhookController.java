package com.nemirovsky.telebot.controller;

import com.nemirovsky.telebot.model.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerRequest;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    private final TelegramBot telegramBot;

    public WebhookController(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

// point for message
    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        logger.info("POST request incoming: " + update);
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
    public String get(ServerRequest request) {
        logger.info("GET request incoming: " + request.headers());
        return "<h1><center>This is Telebot v12 + LOGS testing center</center></h1>";
    }
}
