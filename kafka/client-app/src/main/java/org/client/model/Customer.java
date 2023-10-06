package org.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class Customer {

    private String name;
    private String address;
    @JsonProperty("phone-number")
    private String phoneNumber;
}
