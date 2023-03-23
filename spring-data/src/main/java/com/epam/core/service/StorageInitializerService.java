package com.epam.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Service
public class StorageInitializerService {

    @Autowired
    private EventService eventService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserAccountService userAccountService;

    @PostConstruct
    public void processFiles() throws IOException {
        eventService.loadEventsFromFile();
        userService.loadUsersFromFile();
        ticketService.loadTicketsFromFile();
        userAccountService.loadUserAccountsFromFile();
        log.info("Data from files were loaded into the repository.");
    }
}
