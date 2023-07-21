package com.decode.msapp.users.DTO;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoGet extends UserDto {

    @NotEmpty
    @Min(value = 1, message = "value should be natural number")
    private int id;

}
