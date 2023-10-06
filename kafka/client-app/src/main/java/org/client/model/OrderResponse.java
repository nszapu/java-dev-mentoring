package org.client.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse {

    private String pizza;
    private String comment;
    private Status status;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
}
