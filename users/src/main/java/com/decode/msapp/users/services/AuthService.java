package com.decode.msapp.users.services;


import com.decode.msapp.users.DTO.UserFraudCheckDTO;
import com.decode.msapp.users.exception.CantCheckUserForFraudExeption;
import com.decode.msapp.users.exception.UserIsFraudsterExeption;
import com.decode.msapp.users.exception.WrongInputParameters;
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
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final JwtService jwtService;

    @Value("${app.serviceurl.antifraud}")
    private String antiFraudServiceUrl;

    @Transactional
    public User register(User user) throws WrongInputParameters, UserIsFraudsterExeption {
        userRepository.saveAndFlush(user); //get ID from DB

        String userCheckUrl = antiFraudServiceUrl + "/newcheck/" + user.getId();
        log.info("Checking user with id={} for fraud at URL {}", user.getId(), userCheckUrl);
        UserFraudCheckDTO fraudCheckResponse = restTemplate.getForObject(userCheckUrl, UserFraudCheckDTO.class);

        if (fraudCheckResponse == null)
            throw new CantCheckUserForFraudExeption("Empty result received");
        if (fraudCheckResponse.isFraudster())
            throw new UserIsFraudsterExeption("User is fraudster");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String generateToken(String userName, String userRole) {
        return jwtService.generateToken(userName, userRole);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}