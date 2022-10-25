package com.medias.spring.promo.authorization;


import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Repository
public class JwtTokenRepository implements CsrfTokenRepository {
	private String  ACCESS_CONTROL_EXPOSE_HEADERS = "Token";
	
	@Value("${jwt.secret}")
    private String secret;
	
	@Value("${jwt.keys.public}")
    private String key;
	
	public RSAPublicKey readPublicKey() throws Exception {
		try {
			 byte[] encoded = Base64.getDecoder().decode(key);
			 KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			 X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
			 return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		}catch(Exception e) {
			return null;
		}
	}
	
	public String getSecret() {
		return secret;
	}

    @Override
    public CsrfToken generateToken(HttpServletRequest httpServletRequest) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant());

        String token = "";
        try {
            token = Jwts.builder()
                    .setId(id)
                    .setIssuedAt(now)
                    .setNotBefore(now)
                    .setExpiration(exp)
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();
        } catch (JwtException e) {
            e.printStackTrace();
            //ignore
        }
        return new DefaultCsrfToken("x-csrf-token", "_csrf", token);
    }

    @Override
    public void saveToken(CsrfToken csrfToken, HttpServletRequest request, HttpServletResponse response) {
        if (Objects.nonNull(csrfToken)) {
            if (!response.getHeaderNames().contains(ACCESS_CONTROL_EXPOSE_HEADERS)) {
            	response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, csrfToken.getHeaderName());
            }
             
            if (response.getHeaderNames().contains(csrfToken.getHeaderName())) {
            	response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
            } else {
            	response.addHeader(csrfToken.getHeaderName(), csrfToken.getToken());
            }
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }
    
    public void clearToken(HttpServletResponse response) {
        if (response.getHeaderNames().contains("x-csrf-token")) response.setHeader("x-csrf-token", "");
    }

}