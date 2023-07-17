package com.decode.msapp.users.services;

import com.decode.msapp.users.DTO.FraudCheckResponse;
import com.decode.msapp.users.DTO.UserRegisterDTO;
import com.decode.msapp.users.exception.UserIsFraudsterExeption;
import com.decode.msapp.users.exception.UserIsNotEligibleForFraudTestExeption;
import com.decode.msapp.users.models.User;
import com.decode.msapp.users.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

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

        //Fraud Check
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                    "http://127.0.0.1:8081/antifraud/" + user.getId(),
                        FraudCheckResponse.class);
        if (fraudCheckResponse == null)
            throw new UserIsNotEligibleForFraudTestExeption("Empty result received");

        if (!(fraudCheckResponse.isFraudster())) {
            userRepository.save(user);
        } else {
            throw new UserIsFraudsterExeption("User is fraudster");
        }
    }

}
