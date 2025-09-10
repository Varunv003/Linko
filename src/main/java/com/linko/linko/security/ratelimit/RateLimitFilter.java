package com.linko.linko.security.ratelimit;


import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(1)
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Bucket bucket;

    @Autowired
    public RateLimitFilter(Bucket bucket){
        this.bucket = bucket;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if(path.startsWith("/r/")){
            if (bucket.tryConsume(1)){
                filterChain.doFilter(request, response);
            }else{
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("Rate Limit Exceeded. Try again after some time");
            }
        }else{
            filterChain.doFilter(request, response);
        }

    }
}
