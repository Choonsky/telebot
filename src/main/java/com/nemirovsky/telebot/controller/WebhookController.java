package com.nemirovsky.telebot.controller;

import com.nemirovsky.telebot.model.Dictionary;
import com.nemirovsky.telebot.model.TelegramBot;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Enumeration;
import java.util.List;

import static java.lang.Math.toIntExact;

@RestController
public class WebhookController {

    private final TelegramBot telegramBot;

    public WebhookController(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> updateReceived(@RequestBody Update update, @RequestHeader ServerRequest.Headers headers) {

        Message msg = update.getMessage();

        if (msg == null) {
            if (update.hasCallbackQuery()) {

                String buttonData = update.getCallbackQuery().getData();
                long messageId = update.getCallbackQuery().getMessage().getMessageId();
                long chatId = update.getCallbackQuery().getMessage().getChatId();
                String text = update.getCallbackQuery().getMessage().getText();

                return EditMessageText.builder()
                        .chatId(chatId)
                        .messageId(toIntExact(messageId))
                        .text(text + "\n\n Вы нажали " + buttonData + "!")
                        .build();
            } else {
                System.out.println("Request received: " + headers);
                return null;
            }
        }

        User user = msg.getFrom();

        //TODO: del
        System.out.println("Incoming POST request: " + update);
        String txt;

        String userName = user.getFirstName();

        if (user.getUserName() != null) userName = userName.concat(" \"" + user.getUserName() + "\"");
        if (user.getLastName() != null) userName = userName.concat(" " + user.getLastName());

        String lang = user.getLanguageCode();

        String langText = switch (lang) {
            case "ru" -> "русский";
            case "en" -> "English";
            case "kg" -> "кыргызский";
            case "kz" -> "казахский";
            default -> lang;
        };

        if ("/start".

                equals(msg.getText())) {
            txt = Dictionary.GREETING_01.map.get(lang) + userName + Dictionary.GREETING_02.map.get(lang) + langText
                    + ". <tg-emoji emoji-id=\"5368324170671202286\">\uD83D\uDC4D</tg-emoji>\r\n"
                    + Dictionary.GREETING_03.map.get(lang);
        } else {
            txt = Dictionary.ENTERED.map.get(lang) + msg.getText() + "</b>, " + userName + "!";
        }

        long userId = msg.getFrom().getId();
        long chatId = msg.getChatId();

        InlineKeyboardButton lostButton = InlineKeyboardButton.builder()
                .text(Dictionary.BUTTON_LOST.map.get(lang)).callbackData("lost")
                .build();

        InlineKeyboardButton foundButton = InlineKeyboardButton.builder()
                .text(Dictionary.BUTTON_FOUND.map.get(lang)).callbackData("found")
                .build();

        InlineKeyboardButton infoButton = InlineKeyboardButton.builder()
                .text(Dictionary.BUTTON_INFO.map.get(lang))
                .url("https://telebot.lostfoundpaw.com/info.html")
                .build();

        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(lostButton, foundButton))
                .keyboardRow(List.of(infoButton))
                .build();

        return SendMessage
                .builder()
                .chatId(String.valueOf(chatId))
                .parseMode("HTML")
                .text(txt)
                .replyMarkup(keyboard)
                .build();
    }

//        SendPhoto sendPhoto = SendPhoto.builder()
//                .chatId(chatId)
//                .photo(new InputFile(new File(content.getMediaUrl())))
//                .caption(caption)
//                .parseMode(ParseMode.HTML)
//                .build();

    @GetMapping("/")
    public String get(HttpServletRequest request) {

        System.out.println("Incoming GET request! ");

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.println(key + ": " + value);
        }

        return "<h1><center>This is Telebot v0.1 (AWS) testing center</center></h1>";
    }
}
