package com.decode.msapp.users.DTO;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@SuperBuilder
public class UserDTO {

    //@JsonProperty("username")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    private String name;

   // @JsonProperty("yearOfBirth")
    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

   // @JsonProperty("role")
    @NotEmpty
    private String role;

//    public PersonDTO(Person person) {
//        this.username=person.getUsername();
//        this.yearOfBirth=person.getYearOfBirth();
//        this.role=person.getRole();
//    }

}
