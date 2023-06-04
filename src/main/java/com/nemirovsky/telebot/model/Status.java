package com.nemirovsky.telebot.model;

import lombok.Getter;

@Getter
public enum Status {

    LOST("LOST"),
    FOUND("FOUND"),
    AVAILABLE("AVAILABLE");
    private final String text;

    Status(String text) {
        this.text = text;
    }
}
