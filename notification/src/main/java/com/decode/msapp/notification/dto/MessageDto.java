package com.decode.msapp.notification.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MessageDto {
    @NotNull
    @Size(min = 3, message = "Enter at least 3 chars")
    String messageBody;
}
