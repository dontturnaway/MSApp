package com.decode.msapp.users.services;

import com.decode.msapp.users.model.User;
import com.decode.msapp.users.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor //no need to use constructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository peopleRepository;
    private final ModelMapper mapper;

    public List<User> findAll() {
        return peopleRepository.findAll();
    }

    public User findById(int id) {
        Optional<User> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public User save(User user) {
        return peopleRepository.save(user);
    }

    @Transactional
    public void update(/*int id,*/ User userUpdated) {
        //Here we're getting just a reference (proxy) for future update
        //not querying it from the DB
        //var userToUpdate = peopleRepository.getReferenceById(id);
        peopleRepository.save(userUpdated);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
