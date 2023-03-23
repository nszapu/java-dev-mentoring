package com.epam.core.model;

import java.math.BigDecimal;

public interface UserAccount {
    long getId();
    void setId(long id);
    long getUserId();
    void setUserId(long userId);
    BigDecimal getBalance();
    void setBalance(BigDecimal balance);
}
