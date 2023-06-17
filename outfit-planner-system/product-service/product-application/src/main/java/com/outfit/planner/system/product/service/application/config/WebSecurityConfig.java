package com.outfit.planner.system.product.service.application.config;

import com.outfit.planner.system.product.service.application.security.ProductUserDetailsService;
import com.outfit.planner.system.product.service.application.security.ProductServiceJwtConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final ProductUserDetailsService productUserDetailsService;

    @Value("${product-service.issuers}")
    private List<String> issuers;

    Map<String, AuthenticationManager> authenticationManagers = new HashMap<>();

    JwtIssuerAuthenticationManagerResolver authenticationManagerResolver =
            new JwtIssuerAuthenticationManagerResolver(authenticationManagers::get);

    public WebSecurityConfig(ProductUserDetailsService productUserDetailsService) {
        this.productUserDetailsService = productUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, @Qualifier("product-service-audience-validator")
    OAuth2TokenValidator<Jwt> audienceValidator) throws Exception {
        issuers.forEach(i -> addManager(authenticationManagers, i, audienceValidator));

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .and()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/products/mock**/**").permitAll()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .oauth2ResourceServer(oath -> oath.authenticationManagerResolver(this.authenticationManagerResolver));
        return http.build();
    }

    public void addManager(Map<String, AuthenticationManager> authenticationManagers, String issuer, OAuth2TokenValidator<Jwt> audienceValidator) {
        JwtDecoder jwtDecoder = jwtDecoder(audienceValidator, issuer);

        JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider(jwtDecoder);
        authenticationProvider.setJwtAuthenticationConverter(productQueryJwtConverter());
        authenticationManagers.put(issuer, authenticationProvider::authenticate);
    }

    JwtDecoder jwtDecoder(OAuth2TokenValidator<Jwt> audienceValidator, String issuerUri) {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuerUri);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);
        jwtDecoder.setJwtValidator(withAudience);
        return jwtDecoder;
    }

    Converter<Jwt, ? extends AbstractAuthenticationToken> productQueryJwtConverter() {
        return new ProductServiceJwtConverter(productUserDetailsService);
    }

}
