package com.repository;

import java.util.List;

public interface Repository<T, K> {

    T get(K key);

    List<T> getAll();

    void save(T item);

    T delete(K key);
}
