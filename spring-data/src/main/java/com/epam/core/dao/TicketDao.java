package com.epam.core.dao;

import com.epam.core.Application;
import com.epam.core.entity.TicketEntity;
import com.epam.core.model.Ticket;
import com.epam.core.repository.Storage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketDao {
    @Getter
    @Setter
    private String ticketsPath;
    private List<Ticket> tickets = new ArrayList<>();
    private ObjectMapper mapper = new ObjectMapper();
    private ClassLoader classLoader = Application.class.getClassLoader();
    @Getter
    @Setter
    private Storage repository;

    public void loadTicketsFromFile() throws IOException {
        tickets = Arrays.asList(mapper.readValue(new File(classLoader.getResource(ticketsPath).getFile()), TicketEntity[].class));
        tickets.forEach(event -> repository.save("ticket", event));
    }

    public List<Ticket> getTickets() {
        List<Ticket> result = new ArrayList<>();
        for (Object key: repository.getKeyArray()){
            if (key.toString().startsWith("ticket")){
                result.add((Ticket) repository.get(key.toString()));
            }
        }
        return result;
    }

    public Ticket save(Ticket ticket) {
        repository.save("ticket", ticket);
        return ticket;
    }

    public boolean delete(long id) {
        return repository.delete("ticket", id);
    }
}
