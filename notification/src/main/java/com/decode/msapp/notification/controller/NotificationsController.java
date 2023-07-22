package com.decode.msapp.notification.controller;

import com.decode.msapp.notification.dto.MessageDto;
import com.decode.msapp.notification.dto.NotificationDto;
import com.decode.msapp.notification.service.NotificationService;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/notifications")
public class NotificationsController {

    private final NotificationService notificationService;
    private final ModelMapper mapper;

    @GetMapping()
    public ResponseEntity<List<NotificationDto>> getAll() {
        var result = notificationService.findAll().stream()
                .map(e -> mapper.map(e, NotificationDto.class))
                .toList();
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getById(@PathVariable("id") int id) {
        var notification = notificationService.getById(id);
        if (notification.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No mathces found");
        }
        var result = mapper.map(notification.get(), NotificationDto.class);
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(MessageDto message) {

        return new ResponseEntity<>("Message sent", HttpStatusCode.valueOf(200));
    }


}
