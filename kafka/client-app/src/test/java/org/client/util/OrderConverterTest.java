package org.client.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.client.dao.entity.CustomerEntity;
import org.client.dao.entity.OrderEntity;
import org.client.model.Customer;
import org.client.model.OrderRequest;
import org.client.model.OrderResponse;
import org.client.model.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderConverterTest {

    private static OrderConverter converter;
    private static OrderRequest orderRequest;
    private static OrderEntity orderEntity;
    private static CustomerEntity customerEntity;
    private static OrderResponse orderResponse;

    @BeforeAll
    static void setup() {
        converter = new OrderConverter(new ObjectMapper().registerModule(new JavaTimeModule()));
        orderRequest = OrderRequest
                .builder()
                .pizza("pizza")
                .comment("comment")
                .customer(new Customer("name", "address", "phone-number"))
                .build();
        customerEntity = new CustomerEntity("name", "address", "phone-number");
        orderEntity = new OrderEntity(null, customerEntity, "pizza", "comment", null, null);
        orderResponse = new OrderResponse("pizza", "comment", null, null);
    }

    @Test
    void convertOrderRequestToOrderEntity() {
        OrderEntity actualOrderEntity = converter.convertOrderRequestToOrderEntity(orderRequest);
        assertThat(actualOrderEntity)
                .usingRecursiveComparison()
                .isEqualTo(orderEntity);
    }

    @Test
    void convertOrderEntityToOrderResponse() {
        OrderResponse actualOrderResponse = converter.convertOrderEntityToOrderResponse(orderEntity);
        assertEquals(orderResponse, actualOrderResponse);
    }

    @Test
    void convertOrderRequestToCustomerEntity() {
        CustomerEntity actualCustomerEntity = converter.convertOrderRequestToCustomerEntity(orderRequest);
        assertThat(actualCustomerEntity)
                .usingRecursiveComparison()
                .isEqualTo(customerEntity);
    }
}
