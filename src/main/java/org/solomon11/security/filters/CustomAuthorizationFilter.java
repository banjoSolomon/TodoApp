package org.solomon11.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static org.solomon11.security.utils.SecurityUtils.JWT_PREFIX;
import static org.solomon11.security.utils.SecurityUtils.PUBLIC_ENDPOINTS;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getServletPath();
        boolean isRequestPathPublic = PUBLIC_ENDPOINTS.contains(requestPath);
        if(isRequestPathPublic) filterChain.doFilter(request, response);
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null){
            String token = authorizationHeader.substring(JWT_PREFIX.length()).strip();
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512("secret".getBytes()))
                    .withIssuer("todo_list")
                    .withClaimPresence("roles")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            List<SimpleGrantedAuthority> authorities = decodedJWT.getClaim("roles").asList(SimpleGrantedAuthority.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        filterChain.doFilter(request, response);


    }
}
