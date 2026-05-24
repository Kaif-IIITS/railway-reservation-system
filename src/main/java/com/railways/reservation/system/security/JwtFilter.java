package com.railways.reservation.system.security;

import com.railways.reservation.system.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    private static final String[] PUBLIC_PATHS = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/refresh",
            "/v2/api-docs",
            "/v3/api-docs",
            "/swagger-ui.html",
            "/swagger-ui/",
            "/webjars/",
            "/index.html",
    };

    private boolean isPublicPath(String path) {
        for (String publicPath : PUBLIC_PATHS) {
            if (path.startsWith(publicPath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI().trim();
        log.warn("Processing request for path: {}", request.getRequestURI());
        // Skip JWT validation for public endpoints
        if(isPublicPath(path)){
            filterChain.doFilter(request,response);
            return;
        }

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header==null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = header.substring(7);
        String username = jwtService.extractUsername(token);

        if(username != null &&  SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userService.loadUserByUsername(username);

            if(!jwtService.isTokenValid(token,username)){
                filterChain.doFilter(request,response);
                return ;
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }
        filterChain.doFilter(request,response);
    }
}