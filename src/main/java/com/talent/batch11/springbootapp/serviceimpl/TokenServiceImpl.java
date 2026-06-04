package com.talent.batch11.springbootapp.serviceimpl;


import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.repository.AccountRepository;
import com.talent.batch11.springbootapp.service.TokenService;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;

@Service
public class TokenServiceImpl implements TokenService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${jwt.secretkey}")
    String secretKey;

    @Value("${jwt.accessexpiration}")
    int accessExpiration;

    @Value("${jwt.refreshexpiration}")
    int refreshExpiration;

    @Value("${app-name}")
    String appName;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public String generateAccessToken(Account account) {
        return Jwts.builder()
                .subject(account.getEmail())
                .claim("name", account.getName())
                .claim("account_id", account.getId())
                .claim("email", account.getEmail())
                .claim("ROLE", account.getRole() != null ? account.getRole() : "ROLE_USER")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String generateRefreshToken(Account account) {
        return Jwts.builder()
                .subject(account.getEmail())
                .claim("name", account.getName())
                .claim("account_id", account.getId())
                .claim("email", account.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(getSigningKey())
                .compact();
    }
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    @Override
    public Authentication parseToken(String token) {
        try {
            logger.info("[[Reached 'parse token' method.]]");
            Jws<Claims> jwt = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);

            Claims payload = jwt.getPayload();
            String email = payload.getSubject();
            String roles = payload.get("ROLE", String.class);

            Optional<Account> optional = Optional.ofNullable(accountRepository.findAccountByEmail(email));
            if (optional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found.");
            }

            var authorities = Arrays.stream(roles != null ? roles.split(",") : new String[0])
                    .filter(r -> !r.trim().isEmpty())
                    .map(SimpleGrantedAuthority::new)
                    .toList();


            return UsernamePasswordAuthenticationToken.authenticated(
                    email,
                    null,
                    authorities
            );

        } catch (ExpiredJwtException e) {
            throw new JwtException("Access token is expired.", e);
        } catch (JwtException e) {
            throw new JwtException("Access token is invalid.", e);
        }
    }

    @Override
    public Account getAccountByToken(String token) {
        try {
            logger.info("Reached get account by token method.");
            Jws<Claims> jwt = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);

            String email = jwt.getPayload().getSubject();
            Optional<Account> optional = Optional.ofNullable(accountRepository.findAccountByEmail(email));
            if (optional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found.");
            }
            return optional.get();
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token.");
        }
    }
}

