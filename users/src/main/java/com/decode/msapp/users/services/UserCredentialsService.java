package com.decode.msapp.users.services;

import com.decode.msapp.users.model.User;
import com.decode.msapp.users.repositories.UserRepository;
import com.decode.msapp.users.model.UserDetailsImpl;
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
public class UserCredentialsService implements UserDetailsService {

    private final UserRepository peopleRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> person = peopleRepository.findByName(s);

        if (person.isEmpty()) {
            log.error("User with name \"{}\" not found", s);
            throw new UsernameNotFoundException("User not found!");
        }
        log.info("User is found, proceeding");
        return new UserDetailsImpl(person.get());
    }
}
