package com.outfit.planner.system.outfit.service.config;

import com.outfit.planner.system.outfit.service.security.OutfitsUserDetailsService;
import com.outfit.planner.system.outfit.service.security.ProductServiceJwtConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final OAuth2ResourceServerProperties oAuth2ResourceServerProperties;
    private final OutfitsUserDetailsService outfitsUserDetailsService;

    public WebSecurityConfig(OAuth2ResourceServerProperties oAuth2ResourceServerProperties, OutfitsUserDetailsService outfitsUserDetailsService) {
        this.oAuth2ResourceServerProperties = oAuth2ResourceServerProperties;
        this.outfitsUserDetailsService = outfitsUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/outfits/mock**/**").permitAll()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(productQueryJwtConverter());
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder(@Qualifier("outfit-service-audience-validator")
                          OAuth2TokenValidator<Jwt> audienceValidator) {

        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(
                oAuth2ResourceServerProperties.getJwt().getIssuerUri());
        OAuth2TokenValidator<Jwt> withIssuer =
                JwtValidators.createDefaultWithIssuer(
                        oAuth2ResourceServerProperties.getJwt().getIssuerUri());
        OAuth2TokenValidator<Jwt> withAudience =
                new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }

    Converter<Jwt, ? extends AbstractAuthenticationToken> productQueryJwtConverter() {
        return new ProductServiceJwtConverter(outfitsUserDetailsService);
    }

}
