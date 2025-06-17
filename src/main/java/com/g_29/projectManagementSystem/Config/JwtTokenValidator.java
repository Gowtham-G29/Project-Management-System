package com.g_29.projectManagementSystem.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstants.JWT_HEADER);

        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7).trim();

            try {
                if (jwt.contains(" ")) {
                    throw new BadCredentialsException("JWT should not contain whitespace");
                }

                SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECREAT_KEY.getBytes());
                Claims claim = Jwts.parser().setSigningKey(key).build().parseSignedClaims(jwt).getBody();

                String email = String.valueOf(claim.get("email"));
                String authorities = String.valueOf(claim.get("authorities"));

                List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorityList);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (MalformedJwtException e) {
                System.err.println("Malformed JWT: " + jwt);
                throw new BadCredentialsException("Malformed JWT token", e);
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid Credentials", e);
            }
        }

        filterChain.doFilter(request, response);
    }

}
