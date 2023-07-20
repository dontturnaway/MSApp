package com.decode.msapp.users.services;

import com.decode.msapp.users.DTO.UserDTO;
import com.decode.msapp.users.DTO.UserRegisterDTO;
import com.decode.msapp.users.model.User;
import com.decode.msapp.users.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor //no need to use constructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository peopleRepository;
    private final ModelMapper mapper;

    public List<User> findAll() {
        return peopleRepository.findAll();
    }

    public List<UserDTO> findAllDTO() {
        return findAll().stream()
                .map(e-> mapper.map(e, UserDTO.class))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public User findById(int id) {
        Optional<User> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public UserDTO findByIdDTO(int id) {
        var person = findById(id);
        return mapper.map(person, UserDTO.class);
    }

    @Transactional
    public User save(User user) {
        return peopleRepository.save(user);
    }

    @Transactional
    public User save(UserRegisterDTO personDTORegister) {
        var person=mapper.map(personDTORegister, User.class);
        return save(person);
    }

    @Transactional
    public void update(int id, User user) {
        peopleRepository.save(user);
    }

    @Transactional
    public void update(int id, UserRegisterDTO personDTORegister) {
        var person=mapper.map(personDTORegister, User.class);
        update(person.getId(), person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
