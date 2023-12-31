package com.outfit.planner.system.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
public class IssuerUriValidator {

    private final String issuerUri;
    public IssuerUriValidator(String issuerUri) {
        this.issuerUri = issuerUri;
    }

    public OAuth2TokenValidatorResult validate(Jwt token) {
        Assert.notNull(token, "token cannot be null");

        String iss = token.getClaim("iss").toString();

        return areUrlsEqualWithoutProtocol
                .andThen(createValidatorResult)
                .apply(iss, issuerUri);
    }

    private final Function<Boolean, OAuth2TokenValidatorResult> createValidatorResult = (isValid) -> {
        if (isValid) {
            return OAuth2TokenValidatorResult.success();
        }

        OAuth2Error error = new OAuth2Error("invalid_token", "The iss claim is not valid", null);

        return OAuth2TokenValidatorResult.failure(error);
    };

    private final BiFunction<String, String, Boolean> areUrlsEqualWithoutProtocol = (url1, url2) -> {
        Optional<URL> parsedUrl1 = tryUrl(url1);
        Optional<URL> parsedUrl2 = tryUrl(url2);

        return parsedUrl1.isPresent() && parsedUrl2.isPresent() &&
                parsedUrl1.get().getHost().equals(parsedUrl2.get().getHost()) &&
                parsedUrl1.get().getPath().equals(parsedUrl2.get().getPath());
    };

    private Optional<URL> tryUrl(String s) {
        try {
            return Optional.of(new URL(s));
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

}
