package com.khadijahkelly.helpdesk.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;

@Configuration
public class JwtConfig {

  @Value("${JWT_SECRET}")
  private String jwtSecret;

  @Bean
  public SecretKey jwtSigningKey() {

      byte[] keyBytes = Base64.getDecoder()
          .decode(jwtSecret);
      
      if (keyBytes.length < 32) {
          throw new IllegalStateException(
              "JWT secret must be at least 32 bytes"
          );
      }

    return new SecretKeySpec(keyBytes, "HmacSHA256");
  }

  @Bean
  public JwtEncoder jwtEncoder(SecretKey jwtSigningKey) {

      return new NimbusJwtEncoder(
          new ImmutableSecret<>(jwtSigningKey)
      );
  }

  @Bean
  public JwtDecoder jwtDecoder(SecretKey jwtSigningKey) {

      return NimbusJwtDecoder
          .withSecretKey(jwtSigningKey)
          .macAlgorithm(MacAlgorithm.HS256)
          .build();
  }
}
