package com.application;

import com.exception.SubscriptionNotFoundException;
import com.jmp.bank.api.Bank;
import com.jmp.dto.BankCard;
import com.jmp.dto.BankCardType;
import com.jmp.dto.User;
import com.jmp.service.api.Service;

import java.time.LocalDate;
import java.util.ServiceLoader;

public class Application {

    public static void main(String[] args) {
        Bank bank = ServiceLoader.load(Bank.class).findFirst().orElseThrow();
        BankCard creditCard = bank.createBankCard(
                new User("Dan", "Burn", LocalDate.of(1992, 5, 9)),
                BankCardType.CREDIT
        );
        Service service = ServiceLoader.load(Service.class).findFirst().orElseThrow();
        service.subscribe(creditCard);
        try {
            System.out.println(service.getSubscriptionByBankCardNumber(creditCard.getNumber()).orElseThrow().getBankcard());
        } catch (SubscriptionNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(service.getAverageUserAge());
        service.validateIsPayable();
        System.out.println(service.getAllSubscriptionsByCondition(subscription -> subscription.getStartDate().equals(LocalDate.now())));
    }
}
