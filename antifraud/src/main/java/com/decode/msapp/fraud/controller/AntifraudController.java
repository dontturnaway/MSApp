package com.decode.msapp.fraud.controller;

import com.decode.msapp.fraud.model.FraudCheck;
import com.decode.msapp.fraud.model.User;
import com.decode.msapp.fraud.service.FraudCheckService;
import com.decode.msapp.fraud.service.FraudReportingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<FraudCheck> getAllChecks() {
        return fraudCheckService.getAll();
    }

    @GetMapping("/{checkId}")
    public FraudCheck getChecks(@PathVariable("checkId") Integer checkId) {
        return fraudCheckService.getById(checkId);
    }

    @GetMapping("/newcheck/{userId}")
    public boolean isFraudster(@PathVariable("userId") Integer userId) {
        fraudCheckService.checkByUserId(userId);
        return false;
    }

    @GetMapping("/reporting")
    public Map<User,Boolean> getUsersFraudReporing() {
        return fraudReportingService.getAllUsersWithFraudStatus();
    }

}
