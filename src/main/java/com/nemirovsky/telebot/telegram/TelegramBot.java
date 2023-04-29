package com.nemirovsky.telebot.telegram;

import com.nemirovsky.telebot.exception.NoMessageException;
import com.nemirovsky.telebot.telegram.handler.CallbackQueryHandler;
import com.nemirovsky.telebot.telegram.handler.MessageHandler;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBot extends SpringWebhookBot {
    String botPath;
    String botUsername;
    String botToken;

    MessageHandler messageHandler;
    CallbackQueryHandler callbackQueryHandler;

    public TelegramBot(SetWebhook setWebhook, String botToken, MessageHandler messageHandler,
                       CallbackQueryHandler callbackQueryHandler) {
        super(setWebhook, botToken);
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return handleUpdate(update);
        } catch (IllegalArgumentException e) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    "EXCEPTION_ILLEGAL_MESSAGE");
        } catch (Exception e) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    "EXCEPTION_WHAT_THE_FUCK");
        }
    }

    private BotApiMethod<?> handleUpdate(Update update) throws NoMessageException {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.processCallbackQuery(callbackQuery);
        } else {
            Message message = update.getMessage();
            if (message != null) {
                return messageHandler.handle(update.getMessage(), BotState.DEFAULT);
            } else {
                throw new NoMessageException();
            }
        }
    }
}
