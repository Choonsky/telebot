package com.nemirovsky.telebot.telegram.handler;

import com.nemirovsky.telebot.cache.BotStateCache;
import com.nemirovsky.telebot.cache.EventCache;
import com.nemirovsky.telebot.model.Event;
import com.nemirovsky.telebot.telegram.BotState;
import com.nemirovsky.telebot.model.EventFreq;
import com.nemirovsky.telebot.service.MenuService;
import com.nemirovsky.telebot.telegram.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import static java.lang.Math.toIntExact;

@Component
//processes incoming callback's
public class CallbackQueryHandler {
    private final BotStateCache botStateCache;
    private final EventCache eventCache;
    private final MenuService menuService;
    private final EventHandler eventHandler;

    @Autowired
    public CallbackQueryHandler(BotStateCache botStateCache, EventCache eventCache, MenuService menuService, EventHandler eventHandler) {
        this.botStateCache = botStateCache;
        this.eventCache = eventCache;
        this.menuService = menuService;
        this.eventHandler = eventHandler;
    }

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        final long userId = buttonQuery.getFrom().getId();
        final long messageId = buttonQuery.getMessage().getMessageId();

        String buttonData = buttonQuery.getData();
        String text = buttonQuery.getMessage().getText();

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


        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(toIntExact(messageId))
                .parseMode("HTML")
                .text(text + "\n\n Вы нажали <b>" + buttonData + "</b>!")
                .replyMarkup(keyboard)
                .build();

        /*
        BotApiMethod<?> callBackAnswer = null;

        String data = buttonQuery.getData();

        switch (data) {
            case ("buttonDel"):
                callBackAnswer = new SendMessage(String.valueOf(chatId), "Введите номер напоминания из списка.");
                botStateCache.saveBotState(userId, BotState.ENTERNUMBEREVENT);
                break;
            case ("buttonDelUser"):
                callBackAnswer = new SendMessage(String.valueOf(chatId), "Enter ID User.");
                botStateCache.saveBotState(userId, BotState.ENTERNUMBERUSER);
                break;
            case ("buttonEdit"):
                callBackAnswer = new SendMessage(String.valueOf(chatId), "Введите номер напоминания из списка.");
                botStateCache.saveBotState(userId, BotState.ENTERNUMBERFOREDIT);
                break;
            case ("buttonOneTime"):
                if (botStateCache.getBotStateMap().get(userId).name().equals("ENTERDATE")) {
                    callBackAnswer = eventHandler.saveEvent(EventFreq.TIME, userId, chatId);
                } else {
                    Event event = eventCache.getEventMap().get(userId);
                    event.setFreq(EventFreq.TIME);
                    eventCache.saveEventCache(userId, event);
                    callBackAnswer = eventHandler.editEvent(chatId, userId);
                }
                break;
            case ("buttonOneTimeMonth"):
                if (botStateCache.getBotStateMap().get(userId).name().equals("ENTERDATE")) {
                    callBackAnswer = eventHandler.saveEvent(EventFreq.MONTH, userId, chatId);
                } else {
                    Event event = eventCache.getEventMap().get(userId);
                    event.setFreq(EventFreq.MONTH);
                    eventCache.saveEventCache(userId, event);
                    callBackAnswer = eventHandler.editEvent(chatId, userId);
                }
                break;
            case ("buttonEveryDay"):
                if (botStateCache.getBotStateMap().get(userId).name().equals("ENTERDATE")) {
                    callBackAnswer = eventHandler.saveEvent(EventFreq.EVERYDAY, userId, chatId);
                } else {
                    Event event = eventCache.getEventMap().get(userId);
                    event.setFreq(EventFreq.EVERYDAY);
                    eventCache.saveEventCache(userId, event);
                    callBackAnswer = eventHandler.editEvent(chatId, userId);
                }
                break;
            case ("buttonOneTimeYear"):
                if (botStateCache.getBotStateMap().get(userId).name().equals("ENTERDATE")) {
                    callBackAnswer = eventHandler.saveEvent(EventFreq.YEAR, userId, chatId);
                } else {
                    Event event = eventCache.getEventMap().get(userId);
                    event.setFreq(EventFreq.YEAR);
                    eventCache.saveEventCache(userId, event);
                    callBackAnswer = eventHandler.editEvent(chatId, userId);
                }
                break;
            case ("buttonDate"):
                if (eventCache.getEventMap().get(userId).getEventId() != 0) {
                    callBackAnswer = new SendMessage(String.valueOf(chatId), "Введите дату " +
                            "предстоящего события в формате DD.MM.YYYY HH:MM, например - " +
                            "02.06.2021 21:24, либо 02.06.2021");
                    botStateCache.saveBotState(userId, BotState.EDITDATE);
                } else {
                    callBackAnswer = new SendMessage(String.valueOf(chatId),
                            "Нарушена последовательность действий");
                }
                break;
            case ("buttonDescription"):
                if (eventCache.getEventMap().get(userId).getEventId() != 0) {
                    callBackAnswer = new SendMessage(String.valueOf(chatId), "Введите описание события");
                    botStateCache.saveBotState(userId, BotState.EDITDESCRIPTION);
                } else {
                    callBackAnswer = new SendMessage(String.valueOf(chatId),
                            "Нарушена последовательность действий");
                }
                break;
            case ("buttonHour"):
                callBackAnswer = new SendMessage(String.valueOf(chatId), "Необходимо ввести местное время в формате HH, например, " +
                        "если сейчас 21:45, то введите 21, это необходимо для корректнрого оповещения в соответсвии с вашим часовым поясом.");
                botStateCache.saveBotState(userId, BotState.ENTERTIME);
                break;
            case ("buttonFreq"):
                if (eventCache.getEventMap().get(userId).getEventId() != 0) {
                    SendMessage sendMessage = new SendMessage(String.valueOf(chatId), "Выберите период повторения" +
                            "(Единоразово, 1 раз в месяц в указанную дату, 1 раз в год в указанное число)");
                    botStateCache.saveBotState(userId, BotState.EDITFREQ);
                    sendMessage.setReplyMarkup(menuService.getInlineMessageButtonsForEnterDate());
                    callBackAnswer = sendMessage;
                } else {
                    callBackAnswer = new SendMessage(String.valueOf(chatId),
                            "Нарушена последовательность действий");
                }
        }
        return callBackAnswer;

         */
    }
}
