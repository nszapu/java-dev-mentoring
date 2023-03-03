package com.epam.core.dto;

import com.epam.core.model.User;
import lombok.Data;

@Data
public class UserDto implements User {
    private long id;
    private String name;
    private String email;
}
