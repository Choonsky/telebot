package com.nemirovsky.telebot.telegram.handler;

import com.nemirovsky.telebot.DAO.UserDAO;
import com.nemirovsky.telebot.cache.BotStateCache;
import com.nemirovsky.telebot.cache.EventCache;
import com.nemirovsky.telebot.model.Event;
import com.nemirovsky.telebot.telegram.BotState;
import com.nemirovsky.telebot.service.MenuService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
//processes incoming text message
public class MessageHandler {

    private final UserDAO userDAO;
    private final MenuService menuService;
    private final EventHandler eventHandler;
    private final BotStateCache botStateCache;
    private final EventCache eventCache;

    public MessageHandler(UserDAO userDAO, MenuService menuService, EventHandler eventHandler, BotStateCache botStateCache, EventCache eventCache) {
        this.userDAO = userDAO;
        this.menuService = menuService;
        this.eventHandler = eventHandler;
        this.botStateCache = botStateCache;
        this.eventCache = eventCache;
    }

    public BotApiMethod<?> handle(Message message, BotState botState) {
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        //if new user
        if (!userDAO.isExist(userId)) {
            return eventHandler.saveNewUser(message, userId, sendMessage);
        }
        //save state in to cache
        botStateCache.saveBotState(userId, botState);
        //if state =...
        switch (botState.name()) {
            case ("START"):
                return menuService.getMainMenuMessage(message.getChatId(),
                        "Воспользуйтесь главным меню", userId);
            case ("ENTERTIME"):
                //set time zone user. for correct sent event
                return eventHandler.enterLocalTimeUser(message);
            case ("MYEVENTS"):
                //list events of user
                return eventHandler.myEventHandler(userId);
            case ("ENTERNUMBEREVENT"):
                //remove event
                return eventHandler.removeEventHandler(message, userId);
            case ("ENTERDESCRIPTION"):
                //enter description for create event
                return eventHandler.enterDescriptionHandler(message, userId);
            case ("ENTERDATE"):
                //enter date for create event
                return eventHandler.enterDateHandler(message, userId);
            case ("CREATE"):
                //start create event, set state to next step
                botStateCache.saveBotState(userId, BotState.ENTERDESCRIPTION);
                //set new event to cache
                eventCache.saveEventCache(userId, new Event());
                sendMessage.setText("Введите описание события");
                return sendMessage;
            case ("ENTERNUMBERFOREDIT"):
                //show to user selected event
                return eventHandler.editHandler(message, userId);
            case ("EDITDESCRIPTION"):
                //save new description in database
                return eventHandler.editDescription(message);
            case ("EDITDATE"):
                //save new date in database
                return eventHandler.editDate(message);
            case ("ALLEVENTS"):
                //only admin
                return eventHandler.allEvents(userId);
            case ("ALLUSERS"):
                //only admin
                return eventHandler.allUsers(userId);
            case ("ONEVENT"):
                // on/off notification
                return eventHandler.onEvent(message);
            case ("ENTERNUMBERUSER"):
                //only admin
                return eventHandler.removeUserHandler(message, userId);
            default:
                throw new IllegalStateException("Unexpected value: " + botState);
        }
    }
}
