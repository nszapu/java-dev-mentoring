package com.epam.core.entity;

import com.epam.core.model.User;
import lombok.Data;

@Data
public class UserEntity implements User {
    private long id;
    private String name;
    private String email;
}
