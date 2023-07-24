package com.decode.msapp.notification.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MessageIdDto {

    public MessageIdDto(String id) {
        this.id = Integer.parseInt(id);
    }

    @Min(value = 1, message = "value should be natural number")
    private int id;
}
