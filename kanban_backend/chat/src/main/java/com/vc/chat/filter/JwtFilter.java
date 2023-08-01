package com.vc.chat.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        final String authHeader =((HttpServletRequest) request).getHeader("Authorization");
        if(httpServletRequest.getMethod().equals("OPTIONS")){
            //if the method is options the request can pass through not validation of token is required
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request,response);
        }
        else if(authHeader == null || !authHeader.startsWith("Bearer "))
        {
            throw new ServletException("Missing or Invalid Token");
        }

        String token = authHeader.substring(7);
        //extract the claims
        Claims claims = Jwts.parser().setSigningKey("Kkey").parseClaimsJws(token).getBody();
        // set the claims in the request attribute and pass it to the next handler
        System.out.println("cl ::L "+claims);
        httpServletRequest.setAttribute("claims",claims);
        //pass the claims in the request, anyone wanting to use it
        chain.doFilter(httpServletRequest,httpServletResponse);
    }
}
