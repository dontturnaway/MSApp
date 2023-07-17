package com.decode.msapp.users.services;

import com.decode.msapp.users.DTO.FraudCheckResponse;
import com.decode.msapp.users.DTO.PersonRegisterDTO;
import com.decode.msapp.users.exception.UserIsFraudsterExeption;
import com.decode.msapp.users.exception.UserIsNotEligibleForFraudTestExeption;
import com.decode.msapp.users.models.Person;
import com.decode.msapp.users.repositories.PeopleRepository;
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
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    @Transactional
    public void register(PersonRegisterDTO personRegisterDTO) throws Exception {

        Person person = Person.builder()
                .username(personRegisterDTO.getUsername())
                .yearOfBirth(personRegisterDTO.getYearOfBirth())
                .password(passwordEncoder.encode(personRegisterDTO.getPassword()))
                .role("ROLE_USER")
                .dateCreated(new java.sql.Timestamp(System.currentTimeMillis()))
                .build();
        peopleRepository.saveAndFlush(person); //get ID from DB

        //Fraud Check
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                    "http://127.0.0.1:8081/antifraud/" + person.getId(),
                        FraudCheckResponse.class);
        if (fraudCheckResponse == null)
            throw new UserIsNotEligibleForFraudTestExeption("Empty result received");

        if (!(fraudCheckResponse.isFraudster())) {
            peopleRepository.save(person);
        } else {
            throw new UserIsFraudsterExeption("User is fraudster");
        }




    }

}
