package com.epam.core.dto;

import com.epam.core.model.UserAccount;
import lombok.Data;

@Data
public class UserAccountDto implements UserAccount {
    private long id;
    private long userId;
    private int balance;
}
