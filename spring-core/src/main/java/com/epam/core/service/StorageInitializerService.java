package com.epam.core.service;

import com.epam.core.dao.EventDao;
import com.epam.core.dao.TicketDao;
import com.epam.core.dao.UserDao;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
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
        log.info("Data from files were loaded into the repository.");
    }
}
