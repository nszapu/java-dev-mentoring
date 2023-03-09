package com.epam.core.repository;

import com.epam.core.entity.EventEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends PagingAndSortingRepository<EventEntity, Long> {

    List<EventEntity> findByTitle(String title, Pageable pageable);
    List<EventEntity> findByDate(Date date, Pageable pageable);
}
