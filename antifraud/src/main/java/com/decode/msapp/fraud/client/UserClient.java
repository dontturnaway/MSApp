package com.decode.msapp.fraud.client;

import com.decode.msapp.fraud.model.User;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface UserClient {

    @GetExchange("")
    List<User> getAllUsers();

}
