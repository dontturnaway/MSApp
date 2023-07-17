package com.decode.msapp.users.services;

import com.decode.msapp.users.models.Person;
import com.decode.msapp.users.repositories.PeopleRepository;
import com.decode.msapp.users.security.PersonDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(s);

        if (person.isEmpty()) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found!");
        }
        log.info("User is found, proceeding");
        return new PersonDetails(person.get());
    }
}
