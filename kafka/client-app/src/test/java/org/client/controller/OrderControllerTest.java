package org.client.controller;

import lombok.SneakyThrows;
import org.client.model.OrderRequest;
import org.client.model.OrderResponse;
import org.client.model.Status;
import org.client.service.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private OrderService service;

    private static OrderResponse orderResponse;
    private static String expectedResponseBody;

    @BeforeAll
    public static void setup() {
        orderResponse = new OrderResponse("pizza", "comment", Status.RECEIVED, LocalDate.of(2023, 11, 7));
        expectedResponseBody = """
                        [
                            {
                                "pizza":"pizza",
                                "comment":"comment",
                                "status":"RECEIVED",
                                "date":"2023-11-07"
                            },
                            {
                                "pizza":"pizza",
                                "comment":"comment",
                                "status":"RECEIVED",
                                "date":"2023-11-07"
                            }
                        ]
                        """;
    }

    @SneakyThrows
    @Test
    void getOrders() {
        // given
        when(service.getAllOrders()).thenReturn(new ArrayList<>(){{add(orderResponse); add(orderResponse);}});
        // when
        mvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody));
    }

    @SneakyThrows
    @Test
    void getOrdersByCustomerName() {
        // given
        when(service.getAllOrdersByCustomerName(isA(String.class))).thenReturn(new ArrayList<>(){{add(orderResponse); add(orderResponse);}});
        // when
        mvc.perform(get("/api/orders")
                .param("customerName", "name")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody));
    }

    @SneakyThrows
    @Test
    void createOrder() {
        // given
        when(service.createOrder(isA(OrderRequest.class))).thenReturn(orderResponse);
        String body = """
                    {
                        "pizza": "pizza",
                        "comment": "comment",
                        "customer": {
                            "name": "name",
                            "address": "address",
                            "phone-number": "phone-number"
                        }
                    }
                """;
        // when
        mvc.perform(post("/api/orders")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        // then
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            {
                                "pizza":"pizza",
                                "comment":"comment",
                                "status":"RECEIVED",
                                "date":"2023-11-07"
                            }
                        """));
    }
}
