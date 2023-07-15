package com.decode.msapp.users.services;

import com.decode.msapp.users.models.Person;
import com.decode.msapp.users.repositories.PeopleRepository;
import com.decode.msapp.users.security.PersonDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(s);

        if (person.isEmpty()) {
            System.out.println("PersonDefailsService: user not found");
            throw new UsernameNotFoundException("User not found!");
        }
        System.out.println("PersonDefailsService: user found");
        return new PersonDetails(person.get());
    }
}
