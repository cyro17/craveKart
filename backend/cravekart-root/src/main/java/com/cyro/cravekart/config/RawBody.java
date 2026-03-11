package com.cyro.cravekart.config;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;

public class RawBody implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest,
                       ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
    if (servletRequest instanceof HttpServletRequest httpServletRequest){
      String path = httpServletRequest.getRequestURI();
      if(path.contains("/webhook/stripe")){
        ContentCachingRequestWrapper wrapper =
            new ContentCachingRequestWrapper((HttpServletRequest) servletRequest, 0);
        filterChain.doFilter(wrapper, servletResponse);
        return;
      }
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }
}
