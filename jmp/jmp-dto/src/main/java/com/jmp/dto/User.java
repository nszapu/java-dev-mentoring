package com.jmp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {

    private String name;
    private String surname;
    private LocalDate birthday;
}
