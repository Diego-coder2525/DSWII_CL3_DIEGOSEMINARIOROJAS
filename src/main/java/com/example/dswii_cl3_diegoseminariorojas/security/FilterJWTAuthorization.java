package com.example.dswii_cl3_diegoseminariorojas.security;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FilterJWTAuthorization extends OncePerRequestFilter {

    private final String CLAVE = "123";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            if(validarUToken(request)){
                Claims claims = validarToken(request);
                if(claims.get("authorities") != null){
                    crearAut(claims);
                }else{
                    SecurityContextHolder.clearContext();
                }
            }else{
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request,response);
        }catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }
    private Boolean validarUToken(HttpServletRequest request){
        String aut = request.getHeader("Authorization");
        if(aut == null || !aut.startsWith("Bearer ")){
            return false;
        }
        return true;
    }
    private Claims validarToken(HttpServletRequest request){
        String token = request.getHeader("Authorization")
                .replace("Bearer ","");
        return Jwts.parser().setSigningKey(CLAVE.getBytes())
                .parseClaimsJwt(token).getBody();
    }
    private void crearAut(Claims claims){

        List<String> auths = (List<String>) claims.get("authorities");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(claims.getSubject(),null,
                        auths.stream().map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
