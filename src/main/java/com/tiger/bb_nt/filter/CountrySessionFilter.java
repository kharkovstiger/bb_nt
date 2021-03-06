package com.tiger.bb_nt.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CountrySessionFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (request.getCookies()!=null) {
//            Arrays.stream(request.getCookies())
//                    .filter(c -> "country".equals(c.getName()))
//                    .map(Cookie::getValue)
//                    .findFirst()
//                    .ifPresent(country -> session.setCountry(Country.valueOf(country)));
//        }

        filterChain.doFilter(request, response);
    }
}
