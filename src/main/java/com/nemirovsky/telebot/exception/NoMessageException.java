package com.nemirovsky.telebot.exception;

public class NoMessageException extends Exception {
    public NoMessageException() {
        super("Telegram Bot exception: no message in the incoming POST-request!");
    }

}
