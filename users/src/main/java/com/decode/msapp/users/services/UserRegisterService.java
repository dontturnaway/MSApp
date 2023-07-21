package com.decode.msapp.users.services;

import com.decode.msapp.users.DTO.FraudCheckResponse;
import com.decode.msapp.users.DTO.UserDtoAdd;
import com.decode.msapp.users.exception.UserIsFraudsterExeption;
import com.decode.msapp.users.exception.UserIsNotEligibleForFraudTestExeption;
import com.decode.msapp.users.model.User;
import com.decode.msapp.users.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    @Value("${app.serviceurl.antifraud}")
    private String antiFraudServiceUrl;

    @Transactional
    public User register(User user) throws UserIsNotEligibleForFraudTestExeption, UserIsFraudsterExeption {
        userRepository.saveAndFlush(user); //get ID from DB

        String userCheckUrl = antiFraudServiceUrl + "/fraudchecks/newcheck/" + user.getId();
        log.info("Checking user for fraud: " + userCheckUrl);
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(userCheckUrl, FraudCheckResponse.class);

        if (fraudCheckResponse == null)
            throw new UserIsNotEligibleForFraudTestExeption("Empty result received");
        if (fraudCheckResponse.isFraudster())
            throw new UserIsFraudsterExeption("User is fraudster");

        return userRepository.save(user);
    }
}