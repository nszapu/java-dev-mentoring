package org.client.model;

import lombok.*;

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
