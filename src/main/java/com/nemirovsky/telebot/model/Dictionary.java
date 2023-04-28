package com.nemirovsky.telebot.model;

import java.util.Map;

public enum Dictionary {


    GREETING_01(Map.of(
            "default", "Этот бот помогает при отлове, тьфу, поиске домашних животных и их владельцев, <b>",
            "ru", "Этот бот помогает при отлове, тьфу, поиске домашних животных и их владельцев, <b>",
            "en", "This bot helps to catch, emm, to find lost pets or their owners, <b>")),
    GREETING_02(Map.of(
            "default", "</b>! Мы определили ваш язык как ",
            "ru", "</b>! Мы определили ваш язык как ",
            "en", "</b>! We think your language is ")),
    GREETING_03(Map.of(
            "default", "Выберите кнопку или пункт меню для продолжения...",
            "ru", "Выберите кнопку или пункт меню для продолжения...",
            "en", "Choose a button or menu item to continue...")),

    ENTERED(Map.of(
            "default", "Вы ввели: <b>",
            "ru", "Вы ввели: <b>",
            "en", "You have entered: <b>")),
    BUTTON_LOST(Map.of(
            "default", "Пропал питомец",
                    "ru", "Пропал питомец",
                    "en", "Pet is lost")),
    BUTTON_FOUND(Map.of(
            "default", "Нашёлся кто-то",
            "ru", "Нашёлся кто-то",
            "en", "Pet is found")),
    BUTTON_INFO(Map.of(
            "default", "Информация о нас",
            "ru", "Информация о нас",
            "en", "About us"));

    public final Map<String, String> map;

    Dictionary(Map<String, String> map) {
        this.map = map;
    }
}
