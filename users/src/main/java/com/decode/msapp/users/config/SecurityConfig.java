package com.decode.msapp.users.config;

import com.decode.msapp.users.config.handlers.CustomAccessDeniedHandler;
import com.decode.msapp.users.config.handlers.CustomUnregisteredException;
import com.decode.msapp.users.services.UserCredentialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.security.web.header.HeaderWriterFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final UserCredentialsService userCredentialsService;
    private final RequestHeaderAuthProvider JWTAuthenticationProvider;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //To fully disable filtering
        //return http.csrf().disable().authorizeHttpRequests().anyRequest().permitAll().and().build();
        return http.cors().and().csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers( "/users/register", "/users/token", "/users/refreshtoken",
                        "/users/validate", "/users/denied", "/users/askauth").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(customUnregisteredException())
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .addFilterBefore(requestHeaderAuthFilter(http), HeaderWriterFilter.class) //new
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                 http.getSharedObject(AuthenticationManagerBuilder.class)
                 .authenticationProvider(DAOAuthenticationProvider())
                 .authenticationProvider(JWTAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public AuthenticationProvider DAOAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userCredentialsService);
        authenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public RequestHeaderAuthenticationFilter requestHeaderAuthFilter(HttpSecurity http) throws Exception {
        RequestHeaderAuthenticationFilter filter = new RequestHeaderAuthenticationFilter();
        filter.setPrincipalRequestHeader("loggedInUser");
        filter.setCredentialsRequestHeader("loggedInRole");
        filter.setExceptionIfHeaderMissing(false);
        //filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager(http));
        return filter;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomUnregisteredException customUnregisteredException() {
        return new CustomUnregisteredException();
    }

}

//    @Bean
//    protected AuthenticationManager authenticationManagerJWT() {
//        return new ProviderManager(Collections.singletonList(requestHeaderAuthenticationProvider));
//    }

    /* Old settings
    http
        //.csrf().disable()
        .authorizeHttpRequests()
        //.requestMatchers("/admin").hasRole("ADMIN") //setting @ the controller level
        .requestMatchers("/auth/login", "/auth/registration","/error").permitAll()
        .anyRequest().hasAnyRole("USER", "ADMIN") //.anyRequest().authenticated() //removed cuz we have roles now
        .and()
        .formLogin().loginPage("/auth/login")
        .loginProcessingUrl("/process_login") //where we want to send this form data
        .defaultSuccessUrl("/hello",true)
        .failureUrl("/auth/login?error")
        .and()
        .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");
     */


