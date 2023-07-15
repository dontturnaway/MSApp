package com.decode.msapp.users.config;

import com.decode.msapp.users.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests().anyRequest().permitAll();
        /*
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
         return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder())
                .and()
                .build();
    }

    //    // Sets up authentication
    //    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //        auth.userDetailsService(personDetailsService)
    //                .passwordEncoder(getPasswordEncoder());
    //    }

}
