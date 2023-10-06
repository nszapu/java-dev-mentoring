package org.client.dao.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.client.model.Status;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Table(name = "orders")
public class OrderEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_name")
    private CustomerEntity customer;
    @Column
    @JsonProperty("pizza")
    private String pizza;
    @Column
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("status")
    private String status;
    @CreationTimestamp
    @Column
    @JsonProperty("date")
    private LocalDate date;
}
