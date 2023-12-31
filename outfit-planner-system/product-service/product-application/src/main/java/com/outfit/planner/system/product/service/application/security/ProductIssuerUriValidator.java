package com.outfit.planner.system.product.service.application.security;

import com.outfit.planner.system.security.IssuerUriValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Qualifier("product-service-issuer-validator")
public class ProductIssuerUriValidator implements OAuth2TokenValidator<Jwt> {

    private final OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    public ProductIssuerUriValidator(OAuth2ResourceServerProperties oAuth2ResourceServerProperties) {
        this.oAuth2ResourceServerProperties = oAuth2ResourceServerProperties;
    }

    public OAuth2TokenValidatorResult validate(Jwt token) {
        String issuerUri = oAuth2ResourceServerProperties.getJwt().getIssuerUri();
        IssuerUriValidator issuerUriValidator = new IssuerUriValidator(issuerUri);

        return issuerUriValidator.validate(token);
    }

}
