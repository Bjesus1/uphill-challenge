package com.uphill.challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for Web Security.
 * @EnableWebSecurity -> Authentication is required for all REST requests.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * The plain text username  for the default test user.
     */
    @Value("${security.username}")
    private String username;

    /**
     * The plain text password for the default test user.
     */
    @Value("${security.password}")
    private String password;

    /**
     * A security filter rule.
     *
     * @param http the http security object
     * @return a security rule
     * @throws Exception A generic exception possible to occur.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    /**
     * Returns a default username:password user permission in memory to be used on requests.
     *
     * @return an InMemoryUserDetailsManager object with the user permissions
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username(username)
                .password(password)
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

}