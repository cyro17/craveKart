package com.cyro.cravekart.config.security;

import com.cyro.cravekart.config.SecurityConfig;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return request.getServletPath().startsWith("/api/v1/auth/");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    try {
      final String authorizationHeader = request.getHeader("Authorization");
      if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
        filterChain.doFilter(request, response);
      }

      String token = authorizationHeader.substring(7);
      String username = jwtUtil.gerUsernameFromToken(token);

      if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.get().getUsername(),
              null, user.get().getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
      filterChain.doFilter(request, response);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
