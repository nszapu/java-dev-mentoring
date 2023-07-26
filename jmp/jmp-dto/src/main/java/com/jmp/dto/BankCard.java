package com.jmp.dto;

import java.util.Random;

public class BankCard {

    private String number;
    private User user;

    public BankCard(User user) {
        var rnd = new Random();
        var number = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                number.append(rnd.nextInt(10) % 10);
                if (j == 3 && i != 3) {
                    number.append("-");
                }
            }
        }
        this.number = number.toString();
        this.user = user;
    }

    public String getNumber() {
        return number;
    }
}
