package com.decode.msapp.users.DTO;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@SuperBuilder
public class UserDtoUpdate extends UserDto {

    @NotEmpty
    @Min(value = 1, message = "value should be natural number")
    private int id;

    @NotEmpty
    @Size(min = 2, message = "password should be at least 2 chars")
    private String password;

}
