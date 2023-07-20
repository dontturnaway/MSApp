package com.decode.msapp.fraud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AntifraudService {
    boolean checkByUserId(Integer userId) {
        log.info("Checking for fraud at User ID= " + userId );
        return false;
    }
}
