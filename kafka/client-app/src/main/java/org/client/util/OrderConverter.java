package org.client.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.client.dao.entity.CustomerEntity;
import org.client.dao.entity.OrderEntity;
import org.client.model.OrderRequest;
import org.client.model.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {

    @Autowired
    private ObjectMapper mapper;

    public OrderEntity convertOrderRequestToOrderEntity(OrderRequest orderRequest) {
        return mapper.convertValue(orderRequest, OrderEntity.class);
    }

    public OrderResponse convertOrderEntityToOrderResponse(OrderEntity orderEntity) {
        return mapper.convertValue(orderEntity, OrderResponse.class);
    }

    public CustomerEntity convertOrderRequestToCustomerEntity(OrderRequest orderRequest) {
        return mapper.convertValue(orderRequest.getCustomer(), CustomerEntity.class);
    }
}
