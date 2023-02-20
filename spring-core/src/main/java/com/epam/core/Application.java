package com.epam.core;

import com.epam.core.facade.BookingFacade;
import com.epam.core.service.BookingService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application_context.xml");
        BookingFacade bookingFacade = applicationContext.getBean(BookingService.class);

        bookingFacade.getEventById(1);
    }
}
