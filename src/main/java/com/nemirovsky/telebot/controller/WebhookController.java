package com.nemirovsky.telebot.controller;

import com.nemirovsky.telebot.telegram.Dictionary;
import com.nemirovsky.telebot.telegram.TelegramBot;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

import static java.lang.Math.toIntExact;

@RestController
public class WebhookController {

    private final TelegramBot telegramBot;

    public WebhookController(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> updateReceived(@RequestBody Update update) {

        return telegramBot.onWebhookUpdateReceived(update);
/*
        Message msg = update.getMessage();
        String lang = "default";

        InlineKeyboardButton lostButton = InlineKeyboardButton.builder()
                .text(Dictionary.BUTTON_LOST.map.get(lang))
                .callbackData("lost")
                .build();

        InlineKeyboardButton foundButton = InlineKeyboardButton.builder()
                .text(Dictionary.BUTTON_FOUND.map.get(lang))
                .callbackData("found")
                .build();

        InlineKeyboardButton infoButton = InlineKeyboardButton.builder()
                .text(Dictionary.BUTTON_INFO.map.get(lang))
                .url("https://telebot.lostfoundpaw.com/info.html")
                .build();

        KeyboardButton langButton = KeyboardButton.builder()
                .text(lang)
                .build();

        KeyboardButton helpButton = KeyboardButton.builder()
                .text("?")
                .build();

        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(lostButton, foundButton))
                .keyboardRow(List.of(infoButton))
                .build();

        KeyboardRow row = new KeyboardRow();
        row.add(langButton);
        row.add(helpButton);

        ReplyKeyboardMarkup keyboardMarkup = ReplyKeyboardMarkup.builder()
                .keyboardRow(row)
                .build();

        if (msg == null) {
            if (update.hasCallbackQuery()) {

                String buttonData = update.getCallbackQuery().getData();
                long messageId = update.getCallbackQuery().getMessage().getMessageId();
                long chatId = update.getCallbackQuery().getMessage().getChatId();
                String text = update.getCallbackQuery().getMessage().getText();

                return EditMessageText.builder()
                        .chatId(chatId)
                        .messageId(toIntExact(messageId))
                        .parseMode("HTML")
                        .text(text + "\n\n Вы нажали <b>" + buttonData + "</b>!")
                        .replyMarkup(keyboard)
                        .build();
            } else {
                System.out.println("Request without a message or callback query received: " + headers);
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

        lang = user.getLanguageCode();

        String langText = switch (lang) {
            case "ru" -> "русский";
            case "en" -> "English";
            case "kg" -> "кыргызский";
            case "kz" -> "казахский";
            default -> lang;
        };

        if ("/start".equals(msg.getText())) {
            txt = Dictionary.GREETING_01.map.get(lang) + userName + Dictionary.GREETING_02.map.get(lang) + langText
                    + ". <tg-emoji emoji-id=\"5368324170671202286\">\uD83D\uDC4D</tg-emoji>\r\n"
                    + Dictionary.GREETING_03.map.get(lang);
        } else {
            txt = Dictionary.ENTERED.map.get(lang) + msg.getText() + "</b>, " + userName + "!";
        }

        long userId = msg.getFrom().getId();
        long chatId = msg.getChatId();

        return SendMessage
                .builder()
                .chatId(String.valueOf(chatId))
                .parseMode("HTML")
                .text(txt)
                .replyMarkup("/start".equals(msg.getText()) ? keyboardMarkup : keyboard)
                .build();

 */
    }

    @GetMapping("/")
    public String get() {
        return "<h1><center>This is Telebot v0.5 (AWS) testing center</center></h1>";
    }
}
