package ceos.backend.global.config.jwt;

import ceos.backend.global.config.user.AdminDetails;
import ceos.backend.global.config.user.AdminDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    private final AdminDetailsService adminDetailsService;
    private static final String AUTHORITIES_KEY = "auth";
    @Value("${jwt.secret}")
    private String secret;
    private Key key;


    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    public String createAccessToken(Long id, Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 6);  // 만료일 14일

        final Date issuedAt = new Date();
        final Date validity = new Date(cal.getTimeInMillis());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(id.toString())
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(issuedAt)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 14);  // 만료일 14일

        final Date issuedAt = new Date();
        final Date validity = new Date(cal.getTimeInMillis());


        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(issuedAt)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getTokenUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        AdminDetails adminDetails = (AdminDetails) adminDetailsService.loadAdminByUsername(Long.parseLong(getTokenUserId(token)));
        return new UsernamePasswordAuthenticationToken(adminDetails, token, adminDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info(e.toString());
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
