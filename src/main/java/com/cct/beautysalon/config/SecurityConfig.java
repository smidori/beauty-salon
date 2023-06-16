package com.cct.beautysalon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       //http.authorizeRequests().anyRequest().authenticated();
        //http.authorizeRequests().antMatchers("/users").permitAll();

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/users")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                    ;
        return http.build();
    }

    //                .and()
//                .authorizeRequests()
//                .requestMatchers("/customers/**")
//                .hasRole("ADMIN")
//                .anyRequest()
//                .authenticated()
}
