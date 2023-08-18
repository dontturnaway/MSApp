package com.decode.msapp.users.controllers;

import com.decode.msapp.users.DTO.AuthRequest;
import com.decode.msapp.users.DTO.RefreshTokenRequest;
import com.decode.msapp.users.DTO.TokenResponse;
import com.decode.msapp.users.DTO.UserDtoAdd;
import com.decode.msapp.users.model.Token;
import com.decode.msapp.users.model.User;
import com.decode.msapp.users.model.UserDetailsImpl;
import com.decode.msapp.users.services.AuthService;
import com.decode.msapp.users.services.TokenService;
import com.decode.msapp.users.util.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {

    private final AuthService authService;
    private final ModelMapper mapper;
    private final UserValidator userValidator;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

     @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserDtoAdd userDTOAdd,
                                         BindingResult bindingResult) {
        userValidator.validate(userDTOAdd, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Error in parameters " + bindingResult);
        }
        User userRegistered=mapper.map(userDTOAdd, User.class);
        userRegistered = authService.register(userRegistered); //Controller advice will catch errors
        return ResponseEntity.created(URI.create("")).body("Created User with ID=" + userRegistered.getId());
    }

    @PostMapping("/token")
    public TokenResponse getToken(@RequestBody AuthRequest authRequest)  {
        Authentication authentication;
         try {
             authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (AccessDeniedException | AuthenticationException e) {
             throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong credentials provided");
        }
        var currentUser = ((UserDetailsImpl)authentication.getPrincipal()).getUserObject();
        String accessToken = authService.generateToken(authRequest.getUsername(), currentUser.getRole());
        String refreshToken = tokenService.createRefreshToken(authRequest.getUsername()).getToken();

    return TokenResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    @PostMapping("/refreshtoken")
    public TokenResponse getToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
         return tokenService.findByToken(refreshTokenRequest.getRefreshToken())
                 .map(tokenService::verifyExpiration)
                 .map(Token::getUser)
                 .map(tokenUser -> {
                     String accessToken = authService.generateToken(tokenUser.getName(), tokenUser.getRole());
                     return TokenResponse.builder()
                             .accessToken(accessToken)
                             .refreshToken(refreshTokenRequest.getRefreshToken())
                             .build();

                 }).orElseThrow(() -> new RuntimeException("No refresh token in the DB"));
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }


}
