package com.tiger.bb_nt.filter;

import com.tiger.bb_nt.model.util.CurrentSession;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

public class CountrySessionFilter extends OncePerRequestFilter {

    @Autowired
    ObjectFactory<HttpSession> httpSessionObjectFactory;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("country")).findFirst()
                .ifPresent(c -> CurrentSession.setCountry(c.getValue()));

        filterChain.doFilter(request, response);
    }
}
