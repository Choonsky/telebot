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
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Enumeration;

@RestController
public class WebhookController {

    private final TelegramBot telegramBot;

    public WebhookController(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

// point for message
    @PostMapping("/")
    public BotApiMethod<?> updateReceived(@RequestBody Update update) {

        Message msg = update.getMessage();
        User user = msg.getFrom();

        //TODO: del
        System.out.println("Incoming POST request: " + update);
        String text;

        if ("/start".equals(msg.getText())) {
            text =
                    "Добро пожаловать на сайт по отлову, тьфу, поиску домашних животных и их владельцев, "
                           + user.getFirstName() + " " + user.getLastName() + " по кличке " + user.getUserName()
                            + "! Мы определили ваш язык как " + user.getLanguageCode() + ". Выберите кнопку или " +
                            "введите текст запроса или команду...";
        } else {
            text = "Вы ввели " + msg.getText() + ", " + user.getUserName() + "!";
        }
        long userId = msg.getFrom().getId();
        long chatId = msg.getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        return sendMessage;

        //return telegramBot.onWebhookUpdateReceived(update);
    }

//    try {
//        SendPhoto sendPhoto = new SendPhoto()
//                .setChatId(update.getMessage().getChatId())
//                .setPhoto("Photo", new FileInputStream(new File("/root/index.png")));
//        execute(sendPhoto);
//    } catch (FileNotFoundException e) {
//        e.printStackTrace();
//    } catch (TelegramApiException e) {
//        e.printStackTrace();
//    }


//    public void sendPhotoMessage(long chatId, UserContent content) {
//
//        String caption = getCaption(content);
//
//        SendPhoto sendPhoto = SendPhoto.builder()
//                .chatId(chatId)
//                .photo(new InputFile(new File(content.getMediaUrl())))
//                .caption(caption)
//                .parseMode(ParseMode.HTML)
//                .build();
//
//        try {
//            chronologyBot.execute(sendPhoto);
//        } catch (TelegramApiException e) {
//            log.error("Can't send photo message", e);
//        }
//    }

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
