package com.jmp.cloud.service.impl;

import com.exception.SubscriptionNotFoundException;
import com.jmp.dto.BankCard;
import com.jmp.dto.Subscription;
import com.jmp.dto.User;
import com.jmp.service.api.Service;
import com.repository.SubscriptionRepository;
import com.repository.UserRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Predicate;

public class CloudService implements Service {

    private SubscriptionRepository subscriptionRepository;
    private UserRepository userRepository;

    public CloudService() {
        subscriptionRepository = ServiceLoader.load(SubscriptionRepository.class).findFirst().orElseThrow();
        userRepository = ServiceLoader.load(UserRepository.class).findFirst().orElseThrow();
    }

    @Override
    public void subscribe(BankCard bankcard) {
        subscriptionRepository.save(new Subscription(bankcard.getNumber(), LocalDate.now()));
        userRepository.save(bankcard.getUser());
    }

    @Override
    public Subscription getSubscriptionByBankCardNumber(String cardNumber) throws SubscriptionNotFoundException {
        return (Optional.of(subscriptionRepository.get(cardNumber)).orElseThrow(() -> new SubscriptionNotFoundException("subscription was not found")));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    @Override
    public double getAverageUserAge() {
        return getAllUsers()
                .stream()
                .mapToLong(CloudService::getAge).average().orElseThrow();
//                .collect(Collectors.averagingLong(
//                                user -> getAge(user)
//                ));
    }

    private static long getAge(User user) {
        return ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now());
//        return LocalDate.now().minus(user.getBirthday().getLong(ChronoField.YEAR), ChronoUnit.YEARS).getYear();
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {
        return subscriptionRepository.getAll().stream().filter(condition).toList();
    }
}
