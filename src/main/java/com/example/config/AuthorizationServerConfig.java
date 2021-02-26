package com.example.config;

import com.example.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${security.client.id}")
    private String id;
    @Value("${security.client.secret}")
    private String secret;
    @Value("${security.client.grant_type}")
    private String grant_type;
    @Value("${security.client.refresh.token}")
    private String refreshToken;
    @Value("${security.client.scope_read}")
    private String scope_read;
    @Value("${security.client.scope_write}")
    private String scope_write;
    @Value("${security.client.access.token.valid.time}")
    private int accessTokenValidTime;
    @Value("${security.client.refresh.token.valid.time}")
    private int refreshTokenValidTime;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserServiceImpl userDetailsService;


    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer
                .inMemory()
                .withClient(id)
                .secret(passwordEncoder.encode(secret))
                .authorizedGrantTypes(grant_type, refreshToken)
                .scopes(scope_read, scope_write)
                .accessTokenValiditySeconds(accessTokenValidTime)
                .refreshTokenValiditySeconds(refreshTokenValidTime);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .accessTokenConverter(accessTokenConverter())
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    JwtAccessTokenConverter accessTokenConverter() {
        return new JwtAccessTokenConverter();
    }

}
