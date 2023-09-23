package com.teamback.wise.security.filters;

import com.teamback.wise.exceptions.JWTInvalidException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final List<RequestMatcher> publicEndpoints = Arrays.asList(
            new AntPathRequestMatcher("/h2-console/**"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/actuator/**"),
            new AntPathRequestMatcher("/auth/**"),
            new AntPathRequestMatcher("/error")
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");


        if (this.publicEndpoints.stream().anyMatch(matcher -> matcher.matches(request))) {
            if (authHeader != null && authHeader.startsWith("Google ")) {
                final var googleId = authHeader.replaceAll("^Google ", "");
                request.setAttribute("googleIdToken", googleId);
                logger.info("Obtained users Google ID Token: " + googleId);
            }
            filterChain.doFilter(request, response);
            return;
        } else if (authHeader == null || (authHeader.contains("Bearer") && authHeader.length() < 7)
                || authHeader.isBlank()
                || !authHeader.contains("Bearer")) {
            throw new JWTInvalidException("Invalid JWT Token in Authorization header! It is blank.");
        }

        filterChain.doFilter(request, response);

    }
}
