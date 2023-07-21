package com.decode.msapp.users.util;

import com.decode.msapp.users.DTO.UserDtoAdd;
import com.decode.msapp.users.model.User;
import com.decode.msapp.users.services.UserCredentialsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class PersonValidator implements Validator {

    private final UserCredentialsService userCredentialsService;


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDtoAdd user = (UserDtoAdd)o;
        //its better not to rely on this service and remake it with a new service
        //which returns new Optional
        try {
            userCredentialsService.loadUserByUsername(user.getName());
        } catch (UsernameNotFoundException e) {
            return;
        }
        errors.rejectValue("username", "" , "user with such name already exists");
    }
}
