package com.nemirovsky.telebot.model;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @Column(name = "id")
    private long id;

    // Telegram username like Choonsky (used in Telegram as @Choonsky)
    @Column(name = "user_name")
    private String userName;

    // Telegram extended username like Stanislav "Choonsky" Nemirovsky
    @Column(name = "user_name_ext")
    private String userNameExt;

    // Entered username
    @Column(name = "user_name_entered")
    private String userNameEntered;

    @Column(name = "lang")
    private Locale lang;

    // Status: current type of search
    @Column(name = "status")
    private Status status;

    // Status: current page
    @Column(name = "page")
    private Page page;

    @Column(name = "country")
    private Country country;

    @Column(name = "region")
    private Region region;

    @Column(name = "phone")
    private String phone;

    public User() {
    }
}
