package com.nemirovsky.telebot.service;

import com.nemirovsky.telebot.DAO.EventCacheDAO;
import com.nemirovsky.telebot.DAO.EventDAO;
import com.nemirovsky.telebot.model.Event;
import com.nemirovsky.telebot.model.EventCacheEntity;
import com.nemirovsky.telebot.model.EventFreq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

@EnableScheduling
@Service
public class EventService {
    private final EventDAO eventDAO;
    private final EventCacheDAO eventCacheDAO;

    @Autowired
    public EventService(EventDAO eventDAO, EventCacheDAO eventCacheDAO) {
        this.eventDAO = eventDAO;
        this.eventCacheDAO = eventCacheDAO;
    }

    //start service in 0:00 every day
    @Scheduled(cron = "0 0 0 * * *")
    // @Scheduled(fixedRateString = "${eventservice.period}")
    private void eventService() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        //get event list is now date
        List<Event> list = eventDAO.findAllEvent().stream().filter(event -> {
            if (event.getUser().isOn()) {
                EventFreq eventFreq = event.getFreq();

                //set user event time
                Calendar calendarUserTime = getDateUserTimeZone(event);

                int day1 = calendarUserTime.get(Calendar.DAY_OF_MONTH);
                int month1 = calendarUserTime.get(Calendar.MONTH);
                int year1 = calendarUserTime.get(Calendar.YEAR);
                switch (eventFreq.name()) {
                    case "TIME": //if one time - remove event
                        if (day == day1 && month == month1 && year == year1) {
                            eventDAO.remove(event);
                            return true;
                        } else
                            return false;
                    case "EVERYDAY":
                        return true;
                    case "MONTH":
                        return day == day1;
                    case "YEAR":
                        return day == day1 && month == month1;
                    default:
                        return false;
                }
            } else return false;
        }).toList();

        for (Event event : list) {
            //set user event time
            Calendar calendarUserTime = getDateUserTimeZone(event);
            int hour1 = calendarUserTime.get(Calendar.HOUR_OF_DAY);
            calendarUserTime.set(year, month, day, hour1, 0, 0);

            String description = event.getDescription();
            String userId = String.valueOf(event.getUser().getId());

            //save the event to the database in case the server reboots.
            EventCacheEntity eventCacheEntity = EventCacheEntity.eventTo(calendarUserTime.getTime(),
                    event.getDescription(), event.getUser().getId());
            eventCacheDAO.save(eventCacheEntity);

            //create a thread for the upcoming event with the launch at a specific time
            SendEvent sendEvent = new SendEvent();
            sendEvent.setSendMessage(new SendMessage(userId, description));
            sendEvent.setEventCacheId(eventCacheEntity.getId());

            new Timer().schedule(new SimpleTask(sendEvent), calendarUserTime.getTime());
        }
    }

    private Calendar getDateUserTimeZone(Event event) {
        Calendar calendarUserTime = Calendar.getInstance();
        calendarUserTime.setTime(event.getDate());
        int timeZone = event.getUser().getTimeZone();

        //set correct event time with user timezone
        calendarUserTime.add(Calendar.HOUR_OF_DAY, -timeZone);
        return calendarUserTime;
    }
}
