package com.decode.msapp.notification.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Message {

    @Size(min = 3, message = "Message should be at least 3 char length")
    String messageBody;
}
