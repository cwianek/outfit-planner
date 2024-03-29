package com.outfit.planner.system.outfit.service.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@Qualifier("outfit-service-audience-validator")
public class AudienceValidator implements OAuth2TokenValidator<Jwt> {

    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if (jwt.getAudience().contains("outfit-service")) {
            return OAuth2TokenValidatorResult.success();
        } else {
            OAuth2Error error = new OAuth2Error("invalid_token", "The required audience" + "outfit-service" + "is missing", null);
            return OAuth2TokenValidatorResult.failure(error);
        }
    }
}
