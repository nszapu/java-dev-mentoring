package org.client.dao.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @JsonProperty("name")
    private String name;
    @Column
    @JsonProperty("address")
    private String address;
    @Column(name = "phone_number")
    @JsonProperty("phone-number")
    private String phoneNumber;
}
