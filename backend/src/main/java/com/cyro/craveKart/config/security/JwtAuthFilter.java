package com.cyro.cravekart.config.security;

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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return request.getServletPath().startsWith("/auth/");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    try {
      final String authorizationHeader = request.getHeader("Authorization");
      if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
        filterChain.doFilter(request, response);
        return;
      }

      String token = authorizationHeader.substring(7);
      String username = jwtUtil.getUsernameFromToken(token);

      if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(user,
              null, user.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authToken);
      }
      filterChain.doFilter(request, response);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
