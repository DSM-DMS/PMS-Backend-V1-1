package com.dms.pms.security;

import com.dms.pms.error.exception.BusinessException;
import com.dms.pms.error.exception.ErrorCode;
import com.dms.pms.exception.FailedConnectAppleException;
import com.dms.pms.exception.InvalidTokenException;
import com.dms.pms.security.auth.AuthDetailService;
import com.dms.pms.security.auth.AuthDetails;
import com.dms.pms.security.auth.RoleType;
import com.dms.pms.security.properties.AuthProperties;
import com.dms.pms.utils.api.client.AppleClient;
import com.dms.pms.utils.api.dto.apple.ApplePublicKeyResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final AuthDetailService authDetailService;
    private final AppleClient appleClient;

    private final AuthProperties properties;
    private final AuthProperties.Jwt jwt = properties.getJwt();
    private final AuthProperties.Oauth.Apple apple = properties.getOauth().getApple();

    public String generateAccessToken(String email, RoleType roleType) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + jwt.getAccessExp() * 1000))
                .claim("type", "access_token")
                .claim("role", roleType)
                .signWith(SignatureAlgorithm.HS256, jwt.getSecret())
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwt.getHeader());
        if (bearerToken != null && bearerToken.startsWith(jwt.getPrefix())) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwt.getSecret())
                    .parseClaimsJws(token).getBody().getSubject();
            return true;
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public Authentication getAuthentication(String token) {
        AuthDetails authDetails = authDetailService.loadUserByUsername(this.getEmail(token));
        return new UsernamePasswordAuthenticationToken(authDetails, "", authDetails.getAuthorities());
    }

    public String getEmail(String token) {
        return Jwts.parser().setSigningKey(jwt.getSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public String makeAppleSecret() {
        Date expDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setHeaderParam("kid", apple.getKeyId())
                .setHeaderParam("alg", "ES256")
                .setIssuer(apple.getTeamId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expDate)
                .setAudience("https://appleid.apple.com")
                .setSubject("com.dsm.pms-v2")
                .signWith(SignatureAlgorithm.ES256, getPrivateKey())
                .compact();
    }

    public Claims getClaimsByAppleIdentityToken(String identityToken) {
        try {
            ApplePublicKeyResponse response = appleClient.getApplePublicKey();

            String headerOfIdentityToken = identityToken.substring(0, identityToken.indexOf("."));
            Map<String, String> header = new ObjectMapper().readValue(new String(Base64.getDecoder().decode(headerOfIdentityToken), StandardCharsets.UTF_8), Map.class);
            ApplePublicKeyResponse.Key key = response.getMatchedKeyBy(header.get("kid"), header.get("alg"))
                    .orElseThrow(FailedConnectAppleException::new);

            byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
            byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());

            BigInteger n = new BigInteger(1, nBytes);
            BigInteger e = new BigInteger(1, eBytes);

            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance(key.getKty());
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(identityToken).getBody();
        } catch (InvalidKeySpecException e) {
            throw new InvalidTokenException();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @SneakyThrows
    private PrivateKey getPrivateKey() {
        ClassPathResource resource = new ClassPathResource("pms-authkey.p8");
        String privateKey = new String(Files.readAllBytes(Paths.get(resource.getPath())));
        Reader pemReader = new StringReader(privateKey);
        PEMParser pemParser = new PEMParser(pemReader);
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();

        return converter.getPrivateKey(object);
    }
}
