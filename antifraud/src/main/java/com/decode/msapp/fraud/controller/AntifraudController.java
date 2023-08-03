package com.decode.msapp.fraud.controller;

import com.decode.msapp.fraud.model.FraudCheck;
import com.decode.msapp.fraud.model.User;
import com.decode.msapp.fraud.service.FraudCheckService;
import com.decode.msapp.fraud.service.FraudReportingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/antifraud")
public class AntifraudController {

    private final FraudCheckService fraudCheckService;
    private final FraudReportingService fraudReportingService;

    @GetMapping("/checks")
    public ResponseEntity<List<FraudCheck>> getAllChecks() {
        log.info("All check list requested");
        var result = fraudCheckService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{checkId}")
    public ResponseEntity<FraudCheck> getChecks(@PathVariable("checkId") Integer checkId) {
        var result = fraudCheckService.getById(checkId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/newcheck/{userId}")
    public ResponseEntity<Boolean> isFraudster(@PathVariable("userId") Integer userId) {
        var result = fraudCheckService.checkByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/reporting")
    public ResponseEntity<Map<User,Boolean>> getUsersFraudReporing() {
        var result = fraudReportingService.getAllUsersWithFraudStatus();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
