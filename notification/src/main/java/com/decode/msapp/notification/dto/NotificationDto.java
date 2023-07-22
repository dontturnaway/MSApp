package com.decode.msapp.notification.dto;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class NotificationDto {

    int id;
    int userId;
    Timestamp timeSent;
    String messageBody;


}
