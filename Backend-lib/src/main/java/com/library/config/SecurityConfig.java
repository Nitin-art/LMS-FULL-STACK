package com.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	 @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	     http
	         .cors().configurationSource(corsConfigurationSource())
	         .and()
	         .csrf().disable()
	         .authorizeHttpRequests(auth -> auth
	             .requestMatchers("/api/auth/**").permitAll()
	             .requestMatchers("/api/books/**").permitAll()
	             .requestMatchers(HttpMethod.POST, "/api/users/**").hasRole("ADMIN")  // Only ADMIN can POST
	             .requestMatchers("/api/users/**").authenticated()  // Others must be authenticated
	             .anyRequest().authenticated()
	         );
	     return http.build();
	 }


    // CORS Configuration to allow requests from Angular
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");  // Allow your Angular app
        configuration.addAllowedMethod("*");  // Allow all HTTP methods (GET, POST, etc.)
        configuration.addAllowedHeader("*");  // Allow all headers
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
