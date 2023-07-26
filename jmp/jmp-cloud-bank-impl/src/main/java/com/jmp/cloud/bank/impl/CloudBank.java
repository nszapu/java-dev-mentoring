package com.jmp.cloud.bank.impl;

import com.jmp.bank.api.Bank;
import com.jmp.dto.*;

import java.util.ArrayList;
import java.util.List;

public class CloudBank implements Bank {

    private List<User> users = new ArrayList<>();

    @Override
    public BankCard createBankCard(User user, BankCardType bankCardType) {
        switch (bankCardType) {
            case CREDIT -> {
                users.add(user);
                return new CreditBankCard(user);
            }
            case DEBIT -> {
                users.add(user);
                return new DebitBankCard(user);
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }
}
