package com.decode.msapp.notification.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {

    @Size(min = 3, message = "Message should be at least 3 char length")
    String messageBody;
}
