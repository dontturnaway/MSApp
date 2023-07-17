package com.decode.msapp.users.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class FraudCheckResponse {
    private boolean isFraudster;


}
