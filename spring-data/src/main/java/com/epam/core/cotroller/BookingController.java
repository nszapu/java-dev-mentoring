package com.epam.core.cotroller;

import com.epam.core.facade.BookingFacade;
import com.epam.core.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class BookingController {

    @Autowired
    private BookingFacade bookingFacade;

    @GetMapping(value = "/ticket")
    public ResponseEntity<Ticket> getTestData() {
        Ticket ticket = bookingFacade.bookTicket(1, 1, 4, Ticket.Category.STANDARD);
        return ResponseEntity.ok(ticket);
    }
}
