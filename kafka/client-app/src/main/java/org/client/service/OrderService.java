package org.client.service;

import lombok.extern.slf4j.Slf4j;
import org.client.dao.entity.CustomerEntity;
import org.client.dao.entity.OrderEntity;
import org.client.dao.repository.CustomerRepository;
import org.client.model.OrderMessage;
import org.client.model.OrderRequest;
import org.client.model.OrderResponse;
import org.client.dao.repository.OrderRepository;
import org.client.model.Status;
import org.client.util.OrderConverter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class OrderService {

    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private OrderConverter converter;
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, OrderConverter converter, KafkaTemplate<String, OrderMessage> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.converter = converter;
        this.kafkaTemplate = kafkaTemplate;
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
        kafkaTemplate.send("order", Long.toString(orderEntity.getId()), orderMessage);
        return converter.convertOrderEntityToOrderResponse(orderEntity);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(o -> converter.convertOrderEntityToOrderResponse(o)).toList();
    }

    public List<OrderResponse> getAllOrdersByCustomerName(String customerName) {
        return orderRepository.findAllByCustomerName(customerName).stream().map(o -> converter.convertOrderEntityToOrderResponse(o)).toList();
    }

    public void updateOrder(String key, OrderMessage orderMessage) {
        Long id = Long.parseLong(key);
        orderRepository.findById(id).orElseThrow(NoSuchElementException::new);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(id);
        orderEntity.setPizza(orderMessage.getPizza());
        orderEntity.setComment(orderMessage.getComment());
        orderEntity.setStatus(orderMessage.getStatus().toString());
        orderEntity.setDate(orderMessage.getDate());
        orderEntity.setCustomer(new CustomerEntity(orderMessage.getName(), orderMessage.getAddress(), orderMessage.getPhoneNumber()));
        OrderEntity updatedOrder = orderRepository.save(orderEntity);
        log.info(String.format("Updated %s in db.", updatedOrder));
    }
}
