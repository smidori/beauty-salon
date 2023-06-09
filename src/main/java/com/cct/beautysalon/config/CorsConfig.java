package com.cct.beautysalon.config;

import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

//@Configuration
public class CorsConfig {

    //@Bean
    CorsFilter corsFilter(){
        //System.out.println("*************** cors filter should not load bean and confuration annotations");
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost"));
        //config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowCredentials(true); //browser should send credentials sucha as cookies with cross origin requests
//        config.setAllowedHeaders(Arrays.asList("*"));
//        config.setExposedHeaders(Arrays.asList("*"));
//        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList(
                "Origin",
                "Access-Control-Allow-Origin",
                "Content-Type",
                "Accept",
                "Authorization",
                "Requestor-Type",
                "Origin, Accept",
                "X-Requested-With",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
        ));//accept all headers
        config.setExposedHeaders(Arrays.asList(
                "Origin",
                "Content-Type",
                "Accept",
                "Authorization",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials"
        ));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        var urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", config); //indicate for which url will apply this config
        //CorsFilter corsFilter = new CorsFilter(urlBasedCorsConfigurationSource);
        return new CorsFilter(urlBasedCorsConfigurationSource);

    }
}
