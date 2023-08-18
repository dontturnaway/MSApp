package com.decode.msapp.users.config;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestHeaderAuthProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return new PreAuthenticatedAuthenticationToken(
                authentication.getPrincipal(),
                authentication.getCredentials(),
                /* rolesList*/
                List.of(new Role(authentication.getCredentials().toString()))
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }

    @AllArgsConstructor
    public class Role implements GrantedAuthority {
        private final String role;

        @Override
        public String getAuthority() {
            return role;
        }
    }
}