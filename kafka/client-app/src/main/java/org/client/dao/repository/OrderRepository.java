package org.client.dao.repository;

import org.client.dao.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("select o from OrderEntity o where o.customer.name = :customerName")
    List<OrderEntity> findAllByCustomerName(String customerName);
}
