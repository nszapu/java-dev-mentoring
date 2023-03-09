package com.epam.core.dto;

import com.epam.core.model.UserAccount;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserAccountDto implements UserAccount {
    private long id;
    private long userId;
    private BigDecimal balance;
}
