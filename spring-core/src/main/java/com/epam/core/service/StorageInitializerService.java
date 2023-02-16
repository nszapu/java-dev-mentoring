package com.epam.core.service;

import com.epam.core.Application;
import com.epam.core.dao.EventDao;
import com.epam.core.dao.TicketDao;
import com.epam.core.dao.UserDao;
import com.epam.core.entity.EventEntity;
import com.epam.core.entity.TicketEntity;
import com.epam.core.entity.UserEntity;
import com.epam.core.model.Event;
import com.epam.core.model.Ticket;
import com.epam.core.model.User;
import com.epam.core.repository.Storage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class StorageInitializerService {

    @Getter
    @Setter
    private EventDao eventDao;

    @Getter
    @Setter
    private TicketDao ticketDao;

    @Getter
    @Setter
    private UserDao userDao;

    @PostConstruct
    public void processFiles() throws IOException {
        eventDao.loadEventsFromFile();
        ticketDao.loadTicketsFromFile();
        userDao.loadUsersFromFile();
    }
}
