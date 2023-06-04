package com.nemirovsky.telebot.model;

import lombok.Getter;

@Getter
public enum Species {

    CAT("Cat"),
    DOG ("Dog"),
    RABBIT ("Rabbit or hare"),
    HAMSTER ("Hamster or something like"),
    TURTLE ("Turtle of a kind"),
    OTHER ("Other animal");
    private final String text;

    Species(String text) {
        this.text = text;
    }
}
