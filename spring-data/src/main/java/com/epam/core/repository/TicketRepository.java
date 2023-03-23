package com.epam.core.repository;

import com.epam.core.entity.TicketEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TicketRepository extends PagingAndSortingRepository<TicketEntity, Long> {

    List<TicketEntity> findByUserId(long userId, Pageable pageable);
    List<TicketEntity> findByEventId(long eventId, Pageable pageable);
    List<TicketEntity> findByEventIdAndPlace(long eventId, int place);
}
