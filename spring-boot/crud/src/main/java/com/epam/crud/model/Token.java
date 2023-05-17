package com.epam.crud.model;

import lombok.Data;

@Data
public class Token {

    private long userId;
    private String accessToken;
    private String refreshToken;
}
