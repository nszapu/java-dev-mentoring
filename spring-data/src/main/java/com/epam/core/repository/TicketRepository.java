package com.epam.core.repository;

import com.epam.core.entity.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<TicketEntity, Long> {

    List<TicketEntity> findByUserId(long userId);
    List<TicketEntity> findByEventId(long eventId);
    List<TicketEntity> findByEventIdAndPlace(long eventId, int place);
}
