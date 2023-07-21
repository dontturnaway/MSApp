package com.decode.msapp.users.DTO;


import jakarta.validation.constraints.Min;
import lombok.*;

@Getter @Setter
public class UserIdDTO {

    public UserIdDTO(String id) {
        this.id = Integer.parseInt(id);
    }

    @Min(value = 1, message = "value should be natural number")
    private int id;

}
