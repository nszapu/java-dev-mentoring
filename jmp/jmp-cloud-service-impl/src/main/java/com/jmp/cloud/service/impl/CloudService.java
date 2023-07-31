package com.jmp.cloud.service.impl;

import com.exception.SubscriptionNotFoundException;
import com.jmp.dto.BankCard;
import com.jmp.dto.Subscription;
import com.jmp.dto.User;
import com.jmp.service.api.Service;
import com.repository.SubscriptionRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CloudService implements Service {

    private SubscriptionRepository subscriptionRepository;
    private List<User> users = new ArrayList<>();

    public CloudService() {
        subscriptionRepository = ServiceLoader.load(SubscriptionRepository.class).findFirst().orElseThrow();
        users.add(new User("Dan", "Burn", LocalDate.of(1992, 5, 9)));
        users.add(new User("Dan", "Burn", LocalDate.of(2010, 5, 9)));
        users.add(new User("Dan", "Burn", LocalDate.of(2002, 5, 9)));
    }

    @Override
    public void subscribe(BankCard bankcard) {
        subscriptionRepository.save(new Subscription(bankcard.getNumber(), LocalDate.now()));
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) throws SubscriptionNotFoundException {
        return Optional.of(Optional.of(subscriptionRepository.get(cardNumber)).orElseThrow(() -> new SubscriptionNotFoundException("subscription was not found")));
    }

    @Override
    public List<User> getAllUsers() {
        return users.stream().toList();
    }

    @Override
    public double getAverageUserAge() {
        return getAllUsers()
                .stream()
                .collect(Collectors.averagingLong(
                                user -> LocalDate.now().minus(user.getBirthday().getLong(ChronoField.YEAR), ChronoUnit.YEARS).getYear()
                ));
    }

    public void validateIsPayable() {
        getAllUsers().forEach(user -> System.out.println(Service.isPayableUser(user)));
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {
        return subscriptionRepository.getAll().stream().filter(condition).collect(Collectors.toList());
    }
}
