package com.jmp.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private String name;
    private String surname;
    private LocalDate birthday;

    public User(String name, String surname, LocalDate birthday) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }
}
