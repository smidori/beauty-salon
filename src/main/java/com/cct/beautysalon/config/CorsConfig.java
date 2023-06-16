package com.cct.beautysalon.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class CorsConfig {

    @Bean
    CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //browser should send credentials sucha as cookies with cross origin requests
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost"));
        config.setAllowedHeaders(Collections.singletonList("*"));//accept all headers
        //config.setExposedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        var urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", config); //indicate for which url will apply this config
        //CorsFilter corsFilter = new CorsFilter(urlBasedCorsConfigurationSource);
        return new CorsFilter();

    }
}
