package com.khadijahkelly.helpdesk.service;

import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import com.khadijahkelly.helpdesk.model.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import java.time.Instant;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(User user) {

    Instant now = Instant.now();
    Instant expiresAt = now.plusSeconds(1800);

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("helpdesk-api")
        .issuedAt(now)
        .expiresAt(expiresAt)
        .subject(user.getEmail())
        .claim("userId", user.getId())
        .claim("role", user.getRole().name())
        .build();

    JwsHeader header = JwsHeader.with(MacAlgorithm.HS256)
        .build();

    return jwtEncoder.encode(
        JwtEncoderParameters.from(header, claims)
    ).getTokenValue();
  }
}