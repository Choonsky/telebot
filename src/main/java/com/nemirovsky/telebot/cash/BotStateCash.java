package com.nemirovsky.telebot.cash;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import com.nemirovsky.telebot.model.BotState;
import java.util.HashMap;
import java.util.Map;

@Service
@Setter
@Getter
//Used to save state bot.
public class BotStateCash {
    private final Map<Long, BotState> botStateMap = new HashMap<>();

    public void saveBotState(long userId, BotState botState) {
        botStateMap.put(userId, botState);
    }
}
