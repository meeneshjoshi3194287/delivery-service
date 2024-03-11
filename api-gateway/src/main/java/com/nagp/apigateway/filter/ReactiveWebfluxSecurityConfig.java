package com.nagp.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
//@EnableGlobalMethodSecurity(prePostEnabled=true)
public class ReactiveWebfluxSecurityConfig {
    @Autowired
    private ReactiveAuthenticationManager authenticationManager;
    @Autowired
    private ServerSecurityContextRepository securityContextRepository;

    private static final String[] WHITE_LIST = new String[] {"/user-service/user/login", "/user-service/user/signup"};

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();

        http.securityMatcher(new NegatedServerWebExchangeMatcher(
                ServerWebExchangeMatchers.pathMatchers(WHITE_LIST)));

        return http.authorizeExchange().pathMatchers("/**").hasRole("USER")
                .anyExchange().authenticated().and()
                .securityContextRepository(securityContextRepository)
                .authenticationManager(authenticationManager)
                .addFilterBefore((exchange, chain) -> {

                    return chain.filter(exchange);
                }, SecurityWebFiltersOrder.FIRST)
                .authorizeExchange()
                .and().build();
    }

}