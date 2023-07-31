package com.jmp.service.api;

import com.exception.SubscriptionNotFoundException;
import com.jmp.dto.BankCard;
import com.jmp.dto.Subscription;
import com.jmp.dto.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Service {

    int PAYABLE_AGE = 18;

    void subscribe(BankCard bankCard);
    Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) throws SubscriptionNotFoundException;
    List<User> getAllUsers();

    double getAverageUserAge();

    static boolean isPayableUser(User user) {
        return Period.between(user.getBirthday(), LocalDate.now()).getYears() >= PAYABLE_AGE;
    }

    void validateIsPayable();

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition);
}
