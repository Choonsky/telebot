package com.nemirovsky.telebot.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class TelegramBotConfig {
    @Value("${telegram.webHookPath}")
    String webHookPath;
    @Value("${telegram.botName}")
    String botName;
    @Value("${telegram.botToken}")
    String botToken;

}
