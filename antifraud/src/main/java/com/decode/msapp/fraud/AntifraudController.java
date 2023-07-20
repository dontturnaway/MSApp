package com.decode.msapp.fraud;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/fraudcheck")
public class AntifraudController {

    AntifraudService antifraudService;

    @GetMapping("/{userID}")
    boolean isFraudster(@PathVariable("userID") Integer userId) {
        antifraudService.checkByUserId(userId);
        return false;
    }

}
