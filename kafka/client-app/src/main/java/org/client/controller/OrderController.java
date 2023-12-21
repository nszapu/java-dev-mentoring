package org.client.controller;

import org.client.model.OrderRequest;
import org.client.model.OrderResponse;
import org.client.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrders(@RequestParam Optional<String> customerName) {
        return customerName.map(service::getAllOrdersByCustomerName).orElseGet(service::getAllOrders);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest order) {
        return service.createOrder(order);
    }
}
