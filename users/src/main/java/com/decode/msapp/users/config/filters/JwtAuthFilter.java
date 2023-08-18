package com.decode.msapp.users.config.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/* Another way to auto-auth from JWT-header, inactive now */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userNameReq = request.getHeader("loggedInUser");
        String userRoleReq = request.getHeader("loggedInRole");

        if (userNameReq != null && userRoleReq != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var authToken = new PreAuthenticatedAuthenticationToken(
                    userNameReq,
                    userRoleReq,
                    /* rolesList*/
                    List.of(new JwtAuthFilter.Role(userRoleReq)));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
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
