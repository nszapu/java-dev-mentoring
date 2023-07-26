package com.jmp.cloud.service.impl;

import com.jmp.dto.BankCard;
import com.jmp.dto.Subscription;
import com.jmp.dto.User;
import com.jmp.service.api.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CloudService implements Service {

    private Map<String, Subscription> subscriptions = new HashMap<>();

    @Override
    public void subscribe(BankCard bankcard) {
        subscriptions.put(bankcard.getNumber(), new Subscription(bankcard.getNumber()));
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        return Optional.ofNullable(subscriptions.get(cardNumber));
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
