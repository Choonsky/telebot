package com.nemirovsky.telebot.config;


import com.nemirovsky.telebot.botconfig.TelegramBotConfig;
import com.nemirovsky.telebot.telegram.TelegramBot;
import com.nemirovsky.telebot.telegram.handler.CallbackQueryHandler;
import com.nemirovsky.telebot.telegram.handler.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;


@Configuration
public class AppConfig {
    private final TelegramBotConfig botConfig;

    public AppConfig(TelegramBotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(botConfig.getWebHookPath()).build();
    }

    @Bean
    public TelegramBot springWebhookBot(SetWebhook setWebhook, MessageHandler messageHandler,
                                        CallbackQueryHandler callbackQueryHandler) {
        TelegramBot bot = new TelegramBot(setWebhook, botConfig.getBotToken(), messageHandler, callbackQueryHandler);
        bot.setBotUsername(botConfig.getBotName());
        bot.setBotPath(botConfig.getWebHookPath());

        return bot;
    }
}
