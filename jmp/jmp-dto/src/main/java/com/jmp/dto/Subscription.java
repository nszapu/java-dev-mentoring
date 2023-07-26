package com.jmp.dto;

import java.time.LocalDate;

public class Subscription {

    private String bankcard;
    private LocalDate startDate;

    public Subscription(String bankcard) {
        this.bankcard = bankcard;
        this.startDate = LocalDate.now();
    }

    public String getBankcard() {
        return bankcard;
    }
}
