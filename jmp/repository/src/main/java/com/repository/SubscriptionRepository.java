package com.repository;

import com.jmp.dto.Subscription;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionRepository implements Repository<Subscription, String> {

    private Map<String, Subscription> subscriptions = new HashMap<>();

    @Override
    public Subscription get(String key) {
        return subscriptions.get(key);
    }

    @Override
    public List<Subscription> getAll() {
        return subscriptions.values().stream().toList();
    }

    @Override
    public void save(Subscription item) {
        subscriptions.put(item.getBankcard(), item);
    }

    @Override
    public Subscription delete(String key) {
        return subscriptions.remove(key);
    }
}
