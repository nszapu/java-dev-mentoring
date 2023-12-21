package org.client.service;

import lombok.extern.slf4j.Slf4j;
import org.client.dao.entity.CustomerEntity;
import org.client.dao.entity.OrderEntity;
import org.client.dao.repository.CustomerRepository;
import org.client.dao.repository.OrderRepository;
import org.client.model.OrderMessage;
import org.client.model.OrderRequest;
import org.client.model.OrderResponse;
import org.client.model.Status;
import org.client.util.OrderConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderConverter converter;
    private final MessageProducerService messageProducerService;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, OrderConverter converter, MessageProducerService messageProducerService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.converter = converter;
        this.messageProducerService = messageProducerService;
    }

    public OrderResponse createOrder(OrderRequest orderRequest) {
        CustomerEntity customerEntity = customerRepository.save(converter.convertOrderRequestToCustomerEntity(orderRequest));
        OrderEntity orderEntity = orderRepository.save(converter.convertOrderRequestToOrderEntity(orderRequest));
        OrderMessage orderMessage = OrderMessage.builder()
                .pizza(orderEntity.getPizza())
                .comment(orderEntity.getComment())
                .name(customerEntity.getName())
                .address(customerEntity.getAddress())
                .phoneNumber(customerEntity.getPhoneNumber())
                .status(Status.valueOf(orderEntity.getStatus()))
                .date(orderEntity.getDate())
                .build();
        messageProducerService.send(orderEntity.getId().toString(), orderMessage);
        return converter.convertOrderEntityToOrderResponse(orderEntity);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(converter::convertOrderEntityToOrderResponse).toList();
    }

    public List<OrderResponse> getAllOrdersByCustomerName(String customerName) {
        return orderRepository.findAllByCustomerName(customerName).stream().map(converter::convertOrderEntityToOrderResponse).toList();
    }

    public void updateOrder(String key, OrderMessage orderMessage) {
        UUID uuid = UUID.fromString(key);
        if (orderRepository.existsById(uuid)) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setId(uuid);
            orderEntity.setPizza(orderMessage.getPizza());
            orderEntity.setComment(orderMessage.getComment());
            orderEntity.setStatus(orderMessage.getStatus().toString());
            orderEntity.setDate(orderMessage.getDate());
            orderEntity.setCustomer(new CustomerEntity(orderMessage.getName(), orderMessage.getAddress(), orderMessage.getPhoneNumber()));
            OrderEntity updatedOrder = orderRepository.save(orderEntity);
            log.info("Updated {} in db.", updatedOrder);
        }
    }
}
