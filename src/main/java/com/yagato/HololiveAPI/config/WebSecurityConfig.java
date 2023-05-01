package com.yagato.HololiveAPI.config;

import com.yagato.HololiveAPI.service.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/api/talents/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/generations/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/illustrators/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/riggers/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/models/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//        // this tells Spring Security to use JDBC authentication with our data source
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        // define query to retrieve a user by username
//        jdbcUserDetailsManager.setUsersByUsernameQuery(
//                "SELECT username, password, active FROM users WHERE username=?"
//        );
//
//        // define query to retrieve the authorities/roles by username
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
//                "SELECT username, role FROM roles WHERE username=?"
//        );
//
//        return jdbcUserDetailsManager;
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeHttpRequests(configurer ->
//                configurer
//                        .requestMatchers(HttpMethod.GET, "/api/talents/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/generations/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/illustrators/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/riggers/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/models/**").permitAll()
//
//                        .requestMatchers(HttpMethod.POST, "/api/talents/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/api/generations/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/api/illustrators/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/api/riggers/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/api/models/**").hasRole("ADMIN")
//
//                        .requestMatchers(HttpMethod.PUT, "/api/talents/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/generations/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/illustrators/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/riggers/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/models/**").hasRole("ADMIN")
//
//                        .requestMatchers(HttpMethod.DELETE, "/api/talents/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/api/generations/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/api/illustrators/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/api/riggers/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/api/models/**").hasRole("ADMIN")
//        );
//
//        // use HTTP Basic authentication
//        httpSecurity.httpBasic();
//
//        // disable Cross Site Request Forgery (CSRF)
//        // in general, not requiered for stateless REST APIs that use POST, PUT, DELETE and/or PATCH
//        httpSecurity.csrf().disable();
//
//        return httpSecurity.build();
//    }

}
