package com.outfit.planner.system.product.service.application.config;

import com.outfit.planner.system.product.service.application.security.ProductUserDetailsService;
import com.outfit.planner.system.product.service.application.security.ProductServiceJwtConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final OAuth2ResourceServerProperties oAuth2ResourceServerProperties;
    private final ProductUserDetailsService productUserDetailsService;

    public WebSecurityConfig(OAuth2ResourceServerProperties oAuth2ResourceServerProperties, ProductUserDetailsService productUserDetailsService) {
        this.oAuth2ResourceServerProperties = oAuth2ResourceServerProperties;
        this.productUserDetailsService = productUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .and()
                .cors()
                .disable()
                .authorizeRequests()
                .requestMatchers("/products/mock**/**").permitAll()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(productQueryJwtConverter());
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder(@Qualifier("product-service-audience-validator") OAuth2TokenValidator<Jwt> audienceValidator,
                          @Qualifier("product-service-issuer-validator") OAuth2TokenValidator<Jwt> issuerValidator) {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(oAuth2ResourceServerProperties.getJwt().getIssuerUri());
        OAuth2TokenValidator<Jwt> tokenValidator = new DelegatingOAuth2TokenValidator<>(issuerValidator, audienceValidator);

        jwtDecoder.setJwtValidator(tokenValidator);

        return jwtDecoder;
    }



    private Converter<Jwt, ? extends AbstractAuthenticationToken> productQueryJwtConverter() {
        return new ProductServiceJwtConverter(productUserDetailsService);
    }

}
