package com.epam.core.repository;

import com.epam.core.entity.EventEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends CrudRepository<EventEntity, Long> {

    List<EventEntity> findByTitle(String title);
    List<EventEntity> findByDate(Date date);
}
