package com.jmp.cloud.bank.impl;

import com.jmp.bank.api.Bank;
import com.jmp.dto.*;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class CloudBank implements Bank {

    private static final int BANK_CARD_NUMBER_LENGTH = 16;
    private static final int BOUND = 10;
    private static final Random random = new Random();

    @Override
    public BankCard createBankCard(User user, BankCardType bankCardType) {
        switch (bankCardType) {
            case CREDIT -> {
                return createNewBankCard(CreditBankCard::new, user);
            }
            case DEBIT -> {
                return createNewBankCard(DebitBankCard::new, user);
            }
        }
        return null;
    }

    private static BankCard createNewBankCard(BiFunction<String, User, BankCard> function, User user) {
        return function.apply(generateBankCardNumber(), user);
    }

    private static String generateBankCardNumber() {
        var randomNumbers = random.ints(0, BOUND).limit(BANK_CARD_NUMBER_LENGTH).boxed().toList();
        return randomNumbers.stream().map(Object::toString).collect(Collectors.joining());
    }
}
