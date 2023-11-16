package org.client.service;

import org.client.dao.entity.CustomerEntity;
import org.client.dao.entity.OrderEntity;
import org.client.dao.repository.CustomerRepository;
import org.client.dao.repository.OrderRepository;
import org.client.model.*;
import org.client.util.OrderConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private OrderConverter orderConverter;
    @Mock
    private MessageProducerService messageProducerService;
    @InjectMocks
    private OrderService orderService;
    private static UUID key;
    private static OrderEntity orderEntity;
    private static CustomerEntity customerEntity;
    private static OrderResponse orderResponse;

    @BeforeAll
    static void setup() {
        key = UUID.randomUUID();
        customerEntity = new CustomerEntity("name", "address", "phone-number");
        orderEntity = new OrderEntity(key, customerEntity, "pizza", "comment", Status.RECEIVED.toString(), LocalDate.now());
        orderResponse = new OrderResponse("pizza", "comment", Status.RECEIVED, LocalDate.now());
    }

    @Test
    void createOrder() {
        when(orderConverter.convertOrderRequestToCustomerEntity(isA(OrderRequest.class))).thenReturn(customerEntity);
        when(customerRepository.save(isA(CustomerEntity.class))).thenReturn(customerEntity);
        when(orderConverter.convertOrderRequestToOrderEntity(isA(OrderRequest.class))).thenReturn(orderEntity);
        when(orderRepository.save(isA(OrderEntity.class))).thenReturn(orderEntity);
        doNothing().when(messageProducerService).send(isA(String.class), isA(OrderMessage.class));
        when(orderConverter.convertOrderEntityToOrderResponse(isA(OrderEntity.class))).thenReturn(orderResponse);
        OrderRequest orderRequest = OrderRequest
                .builder()
                .pizza("pizza")
                .comment("comment")
                .customer(new Customer("name", "address", "phone-number"))
                .build();
        OrderResponse actualOrderResponse = orderService.createOrder(orderRequest);
        assertEquals(orderResponse, actualOrderResponse);
    }

    @Test
    void getAllOrders() {
        when(orderRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(orderEntity);
            add(orderEntity);
        }});
        when(orderConverter.convertOrderEntityToOrderResponse(isA(OrderEntity.class))).thenReturn(orderResponse);
        List<OrderResponse> expectedOrderResponses = new ArrayList<>() {{
            add(orderResponse);
            add(orderResponse);
        }};
        List<OrderResponse> actualOrderResponses = orderService.getAllOrders();
        assertEquals(expectedOrderResponses, actualOrderResponses);
    }

    @Test
    void getAllOrdersByCustomerName() {
        when(orderRepository.findAllByCustomerName(isA(String.class))).thenReturn(new ArrayList<>() {{
            add(orderEntity);
            add(orderEntity);
        }});
        when(orderConverter.convertOrderEntityToOrderResponse(isA(OrderEntity.class))).thenReturn(orderResponse);
        List<OrderResponse> expectedOrderResponses = new ArrayList<>() {{
            add(orderResponse);
            add(orderResponse);
        }};
        List<OrderResponse> actualOrderResponses = orderService.getAllOrdersByCustomerName("name");
        assertEquals(expectedOrderResponses, actualOrderResponses);
    }

    @Test
    void updateOrder() {
        OrderMessage message = new OrderMessage("pizza", "comment", Status.RECEIVED, "name", "address", "phone-number", LocalDate.now());
        when(orderRepository.existsById(isA(UUID.class))).thenReturn(true);
        when(orderRepository.save(isA(OrderEntity.class))).thenReturn(orderEntity);
        orderService.updateOrder(key.toString(), message);
    }
}
