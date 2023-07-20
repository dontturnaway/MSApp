package com.decode.msapp.fraud.repository;

import com.decode.msapp.fraud.model.FraudCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudCheckRepository extends JpaRepository<FraudCheck, Integer> {
}
