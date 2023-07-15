package com.decode.msapp.users.services;

import com.decode.msapp.users.models.Person;
import com.decode.msapp.users.repositories.PeopleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(Person person) {

        person.setPassword(passwordEncoder.encode(person.getPassword())); //encoding pwd
        person.setRole("ROLE_USER"); //setting default role

//      Fraud check should be here

        peopleRepository.save(person);
    }

}
