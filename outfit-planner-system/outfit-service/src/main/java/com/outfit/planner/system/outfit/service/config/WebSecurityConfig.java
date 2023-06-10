package com.outfit.planner.system.outfit.service.config;

import com.outfit.planner.system.outfit.service.security.ProductQueryUserDetailsService;
import com.outfit.planner.system.outfit.service.security.ProductServiceJwtConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
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

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final ProductQueryUserDetailsService productQueryUserDetailsService;

    private final OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    @Value("${outfit-service.issuers}")
    private List<String> issuers;

    public WebSecurityConfig(ProductQueryUserDetailsService productQueryUserDetailsService, OAuth2ResourceServerProperties oAuth2ResourceServerProperties) {
        this.productQueryUserDetailsService = productQueryUserDetailsService;
        this.oAuth2ResourceServerProperties = oAuth2ResourceServerProperties;
    }

    Map<String, AuthenticationManager> authenticationManagers = new HashMap<>();

    JwtIssuerAuthenticationManagerResolver authenticationManagerResolver =
            new JwtIssuerAuthenticationManagerResolver(authenticationManagers::get);


    public static void trustSelfSignedSSL() { //TODO check if change of nginx-certs to certs doesnt fix it (were used wrong certificataes in proxy)
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLContext.setDefault(ctx);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, @Qualifier("outfit-service-audience-validator")
    OAuth2TokenValidator<Jwt> audienceValidator) throws Exception {
//        trustSelfSignedSSL();
        issuers.forEach(i -> addManager(authenticationManagers, i, audienceValidator));

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
                .oauth2ResourceServer(oath -> oath.authenticationManagerResolver(this.authenticationManagerResolver));
//                .jwt()
//                .jwtAuthenticationConverter(productQueryJwtConverter());
        return http.build();
    }

    public void addManager(Map<String, AuthenticationManager> authenticationManagers, String issuer, OAuth2TokenValidator<Jwt> audienceValidator) {
        JwtDecoder jwtDecoder = jwtDecoder(audienceValidator, issuer);

        JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider(jwtDecoder);
        authenticationProvider.setJwtAuthenticationConverter(productQueryJwtConverter());
        authenticationManagers.put(issuer, authenticationProvider::authenticate);
    }

    JwtDecoder jwtDecoder(OAuth2TokenValidator<Jwt> audienceValidator, String issuerUri) {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(
                issuerUri);
//                oAuth2ResourceServerProperties.getJwt().getIssuerUri());
        OAuth2TokenValidator<Jwt> withIssuer =
                JwtValidators.createDefaultWithIssuer(
//                        oAuth2ResourceServerProperties.getJwt().getIssuerUri());
                        issuerUri);
        OAuth2TokenValidator<Jwt> withAudience =
                new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);
        jwtDecoder.setJwtValidator(withAudience);
        return jwtDecoder;
    }

    Converter<Jwt, ? extends AbstractAuthenticationToken> productQueryJwtConverter() {
        return new ProductServiceJwtConverter(productQueryUserDetailsService);
    }

}
