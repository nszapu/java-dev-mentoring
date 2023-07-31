package com.jmp.dto;

import lombok.Data;

import java.util.Random;

@Data
public class BankCard {

    private static final int BANK_CARD_NUMBER_LENGTH = 16;
    private static final int BOUND = 10;

    private String number;
    private User user;

    public BankCard(User user) {
        this.number = generateBankCardNumber();
        this.user = user;
    }

    private String generateBankCardNumber() {
        var number = new StringBuilder();
        for (int i = 0; i < BANK_CARD_NUMBER_LENGTH; i++) {
            number.append(generateDigit());
        }
        return number.toString();
    }

    private int generateDigit() {
        var rnd = new Random();
        return rnd.nextInt(BOUND);
    }
}
