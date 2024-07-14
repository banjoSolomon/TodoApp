package org.solomon11.security.config;

import lombok.AllArgsConstructor;
import org.solomon11.security.filters.CustomAuthorizationFilter;
import org.solomon11.security.filters.CustomUsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final CustomAuthorizationFilter authorizationFilter;
    private final AuthenticationManager authenticationManager;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var authenticationFilter = new CustomUsernamePasswordAuthenticationFilter(authenticationManager);
        authenticationFilter.setFilterProcessesUrl("/api/v1/auth");
        return http.csrf(c->c.disable())
                .cors(c->c.disable())
                .sessionManagement(c-> c.sessionCreationPolicy(STATELESS))
                .addFilterAt(authenticationFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(authorizationFilter, CustomUsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(c->c.requestMatchers(POST, "/api/v1/auth").permitAll()
                        .requestMatchers("api/v1/todo").hasAuthority("USER"))
                .build();

    }



}
