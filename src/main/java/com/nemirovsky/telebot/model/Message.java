package com.nemirovsky.telebot.model;

import org.springframework.data.annotation.Id;

import java.util.Locale;

public class Message {

    @Id
    private String id;

    private String msgCode;

    private Page page;

    private Locale lang;

    private String text;

    public Message(String msgCode, String page, Locale lang, String text, String... args) {

    }

}