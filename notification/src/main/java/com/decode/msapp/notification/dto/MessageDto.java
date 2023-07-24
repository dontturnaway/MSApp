package com.decode.msapp.notification.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    @NotNull
    @Size(min = 5, message = "Enter at least 5 chars")
    String messageBody;
}
