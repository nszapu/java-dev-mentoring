package org.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderRequest {

    private String pizza;
    private String comment;
    private Status status = Status.RECEIVED;
    private Customer customer;
}
