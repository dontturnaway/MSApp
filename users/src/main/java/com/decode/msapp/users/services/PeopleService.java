package com.decode.msapp.users.services;

import com.decode.msapp.users.DTO.PersonDTO;
import com.decode.msapp.users.DTO.PersonRegisterDTO;
import com.decode.msapp.users.models.Person;
import com.decode.msapp.users.repositories.PeopleRepository;
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
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final ModelMapper mapper;

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public List<PersonDTO> findAllDTO() {
        return findAll().stream()
                .map(e-> mapper.map(e, PersonDTO.class))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Person findById(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public PersonDTO findByIdDTO(int id) {
        var person = findById(id);
        return mapper.map(person, PersonDTO.class);
    }

    @Transactional
    public Person save(Person person) {
        return peopleRepository.save(person);
    }

    @Transactional
    public Person save(PersonRegisterDTO personDTORegister) {
        var person=mapper.map(personDTORegister, Person.class);
        return save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, PersonRegisterDTO personDTORegister) {
        var person=mapper.map(personDTORegister, Person.class);
        update(person.getId(), person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
