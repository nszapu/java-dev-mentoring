package com.jmp.cloud.bank.impl;

import com.jmp.bank.api.Bank;
import com.jmp.dto.*;
import com.repository.UserRepository;

import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Function;

public class CloudBank implements Bank {

    private UserRepository userRepository;

    public CloudBank() {
        userRepository = ServiceLoader.load(UserRepository.class).findFirst().orElseThrow();
    }

    @Override
    public BankCard createBankCard(User user, BankCardType bankCardType) {
        switch (bankCardType) {
            case CREDIT -> {
                userRepository.save(user);
                return ((Function<User, CreditBankCard>) CreditBankCard::new).apply(user);
            }
            case DEBIT -> {
                userRepository.save(user);
                return ((Function<User, DebitBankCard>) DebitBankCard::new).apply(user);
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }
}
