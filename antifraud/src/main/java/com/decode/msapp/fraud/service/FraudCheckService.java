package com.decode.msapp.fraud.service;

import com.decode.msapp.fraud.model.User;
import com.decode.msapp.fraud.repository.FraudCheckRepository;
import com.decode.msapp.fraud.model.FraudCheck;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class FraudCheckService {

    private final FraudCheckRepository fraudCheckRepository;

    public boolean checkByUserId(Integer userId) {
        log.info("Checking for fraud for User ID={}", userId);
        return false;
    }

    public Map<User, Boolean> checkByUserIdCollection(List<User> users) {
        log.info("Checking for fraud for groups ID={}", users);
        return users.stream().collect(Collectors.toMap(o -> o, z -> false));
    }

    public FraudCheck getById(Integer id) {
        Optional<FraudCheck> foundCheck = fraudCheckRepository.findById(id);
        return foundCheck.orElse(null);
    }

    public List<FraudCheck> getAll() {
        return fraudCheckRepository.findAll();
    }

    public FraudCheck save(FraudCheck fraudCheck) {
        return fraudCheckRepository.save(fraudCheck);
    }
}
