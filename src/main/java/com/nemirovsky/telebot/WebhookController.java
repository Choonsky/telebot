package com.nemirovsky.telebot;

import com.nemirovsky.telebot.telegram.TelegramBot;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebhookController {

    private final TelegramBot telegramBot;

    public WebhookController(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> updateReceived(@RequestBody Update update) {

        return telegramBot.onWebhookUpdateReceived(update);
    }

    @GetMapping("/")
    public String get() {
        return "<h1><center>This is Telebot v0.7 (AWS) testing center</center></h1>";
    }
}
