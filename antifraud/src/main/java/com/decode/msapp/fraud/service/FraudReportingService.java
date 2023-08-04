package com.decode.msapp.fraud.service;

//import com.decode.msapp.fraud.client.UserClient;
import com.decode.msapp.fraud.client.UserClient;
import com.decode.msapp.fraud.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class FraudReportingService {

    private final UserClient userClient;
    private final FraudCheckService fraudCheckService;

    public List<User> getAllUsers() {
        return userClient.getAllUsers();
    }

    public Map<User,Boolean> getAllUsersWithFraudStatus() {
        return fraudCheckService.checkByUserIdCollection(getAllUsers());
    }

}
