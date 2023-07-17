package com.decode.msapp.users.DTO;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@RequiredArgsConstructor
public class UserRegisterDTO extends UserDTO {

    @NotEmpty
    private String password;

}
