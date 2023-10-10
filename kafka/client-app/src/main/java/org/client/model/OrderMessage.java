package org.client.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderMessage {

    private String pizza;
    private String comment;
    private Status status;
    private String name;
    private String address;
    private String phoneNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
