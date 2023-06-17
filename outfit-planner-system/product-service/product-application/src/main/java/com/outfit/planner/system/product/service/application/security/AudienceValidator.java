package com.outfit.planner.system.product.service.application.security;

import com.outfit.planner.system.domain.config.ProductServiceConfigData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@Qualifier("product-service-audience-validator")
public class AudienceValidator implements OAuth2TokenValidator<Jwt> {

    private final ProductServiceConfigData productServiceConfigData;

    public AudienceValidator(ProductServiceConfigData productServiceConfigData) {
        this.productServiceConfigData = productServiceConfigData;
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if (jwt.getAudience().contains(productServiceConfigData.getCustomAudience())) {
            return OAuth2TokenValidatorResult.success();
        } else {
            OAuth2Error error = new OAuth2Error("invalid_token", "The required audience" + productServiceConfigData.getCustomAudience() + "is missing", null);
            return OAuth2TokenValidatorResult.failure(error);
        }
    }
}
