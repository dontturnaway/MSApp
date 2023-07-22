package com.decode.msapp.fraud.repository;

import com.decode.msapp.fraud.model.FraudCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraudCheckRepository extends JpaRepository<FraudCheck, Integer> {
}
