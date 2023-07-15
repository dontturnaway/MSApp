package com.decode.msapp.fraud;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/antifraud")
public class FraudCheckRESTController {

    FraudCheckService fraudCheckService;

    @GetMapping("/check/{userID}")
    boolean isFraudster(@PathVariable("userID") Integer userId) {
        log.info("Checking for fraud at User ID= " + userId );
        fraudCheckService.checkByUserId(userId);
        return false;
    }

//    @GetMapping("/check/{userID}")
//    FraudCheckResponse isFraudster(@PathVariable("userID") Integer userId) {
//        log.info("Checking for fraud at User ID= " + userId );
//        fraudCheckService.checkByUserId(userId);
//        return false;
//    }

}
