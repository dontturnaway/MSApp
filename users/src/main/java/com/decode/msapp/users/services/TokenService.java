package com.decode.msapp.users.services;

import com.decode.msapp.users.model.Token;
import com.decode.msapp.users.model.User;
import com.decode.msapp.users.repositories.TokenRepository;
import com.decode.msapp.users.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public Token createRefreshToken(String username) {
        Optional<User> user = userRepository.findByName(username);
        if (user.isEmpty()) throw new RuntimeException("Can't find Token for refresh");
        Token token = Token.builder()
                    .user(user.get())
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(600000))//10
                    .build();
        return tokenRepository.save(token);
    }

    public Optional<Token> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public Token verifyExpiration(Token token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            tokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

}
