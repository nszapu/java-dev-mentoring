package com.epam.crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserResponse {

    private String name;
    private String email;
}
