package com.nemirovsky.telebot.controller;

import com.nemirovsky.telebot.model.Dictionary;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

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

        if ("/start".equals(msg.getText())) {
            txt = Dictionary.GREETING_01.map.get(lang) + userName + Dictionary.GREETING_02.map.get(lang) + langText
                    + ". <tg-emoji emoji-id=\"5368324170671202286\">\uD83D\uDC4D</tg-emoji>\r\nВыберите "
                    + Dictionary.GREETING_03.map.get(lang);
        } else {
            txt = Dictionary.ENTERED.map.get(lang) + msg.getText() + ", " + userName + "!";
        }
        long userId = msg.getFrom().getId();
        long chatId = msg.getChatId();

        var next = InlineKeyboardButton.builder()
                .text("").callbackData("next")
                .build();

        var back = InlineKeyboardButton.builder()
                .text("Back").callbackData("back")
                .build();

        var url = InlineKeyboardButton.builder()
                .text("Tutorial")
                .url("https://core.telegram.org/bots/api")
                .build();

        return SendMessage.builder().chatId(String.valueOf(chatId))
                .parseMode("HTML").text(txt).build();

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

        return "<h1><center>This is Telebot v0.1 (AWS) testing center</center></h1>";
    }
}
