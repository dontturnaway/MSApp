package com.decode.msapp.fraud.controller;

import com.decode.msapp.fraud.model.FraudCheck;
import com.decode.msapp.fraud.service.AntifraudService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/fraudchecks")
public class AntifraudController {

    private final AntifraudService antifraudService;

    @GetMapping
    public List<FraudCheck> getAllChecks() {
        return antifraudService.getAll();
    }

    @GetMapping("/{checkId}")
    public FraudCheck getChecks(@PathVariable("checkId") Integer checkId) {
        return antifraudService.getById(checkId);
    }

    @GetMapping("/newcheck/{userId}")
    public boolean isFraudster(@PathVariable("userId") Integer userId) {
        antifraudService.checkByUserId(userId);
        return false;
    }


}
