package com.decode.msapp.users.DTO;


import com.decode.msapp.users.models.Person;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@RequiredArgsConstructor
public class PersonRegisterDTO extends PersonDTO {

    @NotEmpty
    private String password;

}
