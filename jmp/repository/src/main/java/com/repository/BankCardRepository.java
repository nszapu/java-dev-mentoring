package com.repository;

import com.jmp.dto.BankCard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankCardRepository implements Repository<BankCard, String> {

    private Map<String, BankCard> bankcards = new HashMap<>();

    @Override
    public BankCard get(String key) {
        return bankcards.get(key);
    }

    @Override
    public List<BankCard> getAll() {
        return bankcards.values().stream().toList();
    }

    @Override
    public void save(BankCard item) {
        bankcards.put(item.getNumber(), item);
    }

    @Override
    public BankCard delete(String key) {
        return bankcards.remove(key);
    }
}
