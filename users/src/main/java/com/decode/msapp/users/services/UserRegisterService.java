package com.decode.msapp.users.services;

import com.decode.msapp.users.DTO.FraudCheckResponse;
import com.decode.msapp.users.DTO.UserRegisterDTO;
import com.decode.msapp.users.exception.UserIsFraudsterExeption;
import com.decode.msapp.users.exception.UserIsNotEligibleForFraudTestExeption;
import com.decode.msapp.users.models.User;
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
    public void register(UserRegisterDTO personRegisterDTO) throws Exception {

        User user = User.builder()
                .name(personRegisterDTO.getName())
                .yearOfBirth(personRegisterDTO.getYearOfBirth())
                .password(passwordEncoder.encode(personRegisterDTO.getPassword()))
                .role("ROLE_USER")
                .dateCreated(new java.sql.Timestamp(System.currentTimeMillis()))
                .build();
        userRepository.saveAndFlush(user); //get ID from DB

        String userCheckUrl = antiFraudServiceUrl+"/fraudcheck/"+ user.getId();
        log.info("Checking user for fraud: " + userCheckUrl);
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                                            userCheckUrl, FraudCheckResponse.class);
        if (fraudCheckResponse == null)
            throw new UserIsNotEligibleForFraudTestExeption("Empty result received");

        if (!(fraudCheckResponse.isFraudster())) {
            userRepository.save(user);
        } else {
            throw new UserIsFraudsterExeption("User is fraudster");
        }
    }

}
