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
        BankCard debitCard = bank.createBankCard(
                new User("Clark", "Kent", LocalDate.of(2010, 5, 9)),
                BankCardType.DEBIT
        );
        Service service = ServiceLoader.load(Service.class).findFirst().orElseThrow();
        service.subscribe(creditCard);
        service.subscribe(debitCard);
        System.out.println("The latest subscriber's bankcard number:");
        try {
            System.out.println(service.getSubscriptionByBankCardNumber(debitCard.getNumber()).getBankcard());
        } catch (SubscriptionNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Listing users:");
        System.out.println(service.getAllUsers());
        service.getAllUsers().forEach(user -> System.out.printf("%s %s is valid to pay: %b%n", user.getName(), user.getSurname(), Service.isPayableUser(user)));
        System.out.printf("The average age of users: %f%n", service.getAverageUserAge());
        System.out.println("Listing all subscriptions that were created today");
        System.out.println(service.getAllSubscriptionsByCondition(subscription -> subscription.getStartDate().equals(LocalDate.now())));
    }
}
