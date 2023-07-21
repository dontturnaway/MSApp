package com.decode.msapp.users.DTO;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoAdd extends UserDto {

    @NotEmpty
    @Size(min = 2, message = "password should be at least 2 chars")
    private String password;

}
