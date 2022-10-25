package com.medias.spring.promo.authorization;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtFilter extends OncePerRequestFilter {
	
	private final String AUTHORIZATION_HEADER_NAME="Authorization";

    private final JwtTokenRepository tokenRepository;

    private final HandlerExceptionResolver resolver;

	public JwtFilter(JwtTokenRepository tokenRepository, HandlerExceptionResolver resolver) {
        this.tokenRepository = tokenRepository;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	 try {
        	 String header = request.getHeader(AUTHORIZATION_HEADER_NAME);
        	 if(header == null) throw new JwtException("Відсутній заголовок авторизації");

             Matcher matcher = Pattern.compile("^Bearer (.*?)$").matcher(header);
             if (!matcher.find()) throw new JwtException("Заголовок авторизації повинен містити Bearer токен");
             
             String token = matcher.group(1);
             Jwts.parser().setSigningKey(tokenRepository.readPublicKey()).parseClaimsJws(token);
             filterChain.doFilter(request, response);
        }catch (JwtException e) {
        	response.sendError(401, e.getMessage());
        }  
        catch (Exception e) {
        	response.sendError(400, e.getMessage());
        } 
    }
}