package com.decode.msapp.apigw.config;

import com.decode.msapp.apigw.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*
    Filters inbound requests for auth Token, might consider to use GlobalFilter
    for requests to ALL microservices
 */

@Component
@Slf4j
public class UserAuthenticationFilter extends AbstractGatewayFilterFactory<UserAuthenticationFilter.Config> {

    private final RouteValidator routeValidator;
    private final JwtService jwtService;

    public UserAuthenticationFilter(RouteValidator routeValidator, JwtService jwtService) {
        super(Config.class);
        this.routeValidator=routeValidator;
        this.jwtService=jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = null;
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    log.error("No authorization Token was provided in the request's header");
                    return onError(exchange, "No Authorization header.", HttpStatus.UNAUTHORIZED);
                }

                try {
                    String authHeader = extractToken(exchange);
                    validateToken(authHeader);
                    request = addCredentialsToRequest(exchange, authHeader); /* External check of the token is valid */
                } catch (Exception e) {
                    log.error("Token can't be validated, Exception: {}, Message: {} ", e.getClass(), e.getMessage());
                    return onError(exchange, "The Token is invalid or can't be checked", HttpStatus.UNAUTHORIZED);
                }
            }
            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader = authHeader.substring(7);
        }
        return authHeader;
    }

    /* Internal check of the token to be valid */
    private void validateToken(String authHeaderSearch) {
        jwtService.validateToken(authHeaderSearch);
    }

    private ServerHttpRequest addCredentialsToRequest(ServerWebExchange exchange, String authHeader) {
        ServerHttpRequest request;
        var userNameJWT=jwtService.extractUsername(authHeader);
        var userRoleJWT=jwtService.extractUserRole(authHeader);
        request = exchange.getRequest()
                .mutate()
                .header("loggedInUser", userNameJWT)
                .header("loggedInRole", userRoleJWT)
                .build();
        return request;
    }

    /* Redirects to auth error page if there was no Token, or it's invalid */
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        log.error(err);
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    public static class Config {
    }

}
