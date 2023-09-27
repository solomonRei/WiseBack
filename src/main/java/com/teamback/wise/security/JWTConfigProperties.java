package com.teamback.wise.security;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Data
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JWTConfigProperties {

    @NotNull
    private RSAPublicKey publicKey;

    @NotNull
    private RSAPrivateKey privateKey;

    private String audience;

    private String issuer;

    private int expirationTimeAccessTokenMn;

    private int expirationTimeRefreshTokenMn;

}
