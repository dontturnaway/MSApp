package com.decode.msapp.fraud.service;

import com.decode.msapp.fraud.repository.FraudCheckRepository;
import com.decode.msapp.fraud.model.FraudCheck;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AntifraudService {

    private final FraudCheckRepository fraudCheckRepository;

    public boolean checkByUserId(Integer userId) {
        log.info("Checking for fraud for User ID={}", userId);
        return false;
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
