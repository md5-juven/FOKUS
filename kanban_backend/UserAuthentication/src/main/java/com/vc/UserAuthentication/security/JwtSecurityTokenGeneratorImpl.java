package com.vc.UserAuthentication.security;

import com.vc.UserAuthentication.domain.Employee;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtSecurityTokenGeneratorImpl implements SecurityTokenGenerator{

    @Override
    public Map<String, String> createToken(Employee employee) {
        System.out.println(employee);
        String token = null;
        Map<String, Object> claims = new HashMap<>();
        claims.put("email",employee.getEmail());
        claims.put("username",employee.getUsername());
        claims.put("title", employee.getTitle());
        token = generateToken(claims,employee.getEmail());
        Map<String, String> result = new HashMap<>();
        result.put("token",token);
        result.put("message","Successfully Logged In");
        result.put("username", employee.getUsername());
        result.put("email",employee.getEmail());
        result.put("title", employee.getTitle());
        return result;
    }

    public String generateToken(Map<String,Object> claims, String subject){
        String jwtToken = Jwts.builder().setIssuer("K-Auth")
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"Kkey")
                .compact();
        return jwtToken;
    }
}
