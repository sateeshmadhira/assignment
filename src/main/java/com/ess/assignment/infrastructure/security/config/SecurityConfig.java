package com.ess.assignment.infrastructure.security.config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.ReactiveAuthenticationManager;
//import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
//import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
//import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
//import org.springframework.web.cors.reactive.CorsConfigurationSource;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;

//@Configuration
public class SecurityConfig {
//    @Bean
//    public MapReactiveUserDetailsService userDetailsRepository() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("user")
//                .roles("USER", "ADMIN")
//                .build();
//        return new MapReactiveUserDetailsService(user);
//    }
//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http .cors(corsSpec -> corsSpec.configurationSource(corsConfigurationSource())).csrf(ServerHttpSecurity.CsrfSpec::disable)
//                    .authorizeExchange(authorize -> authorize
//                            .pathMatchers("/api/customer/**", "/api/contract/**","/api/customer/getAllCustomers/**")
//                            .permitAll() .anyExchange().authenticated() )
//                .httpBasic().and()
//                .authenticationManager(bearerAuthenticationManager());
//        return http.build();
//    }
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.setAllowedOrigins(List.of("http://localhost:3000"));
//        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        corsConfig.setAllowedHeaders(List.of("*"));
//        corsConfig.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);
//        return source;
//    }
//    private AuthenticationWebFilter basicAuthenticationFilter() {
//        UserDetailsRepositoryReactiveAuthenticationManager authManager;
//        AuthenticationWebFilter basicAuthenticationFilter;
//        ServerAuthenticationSuccessHandler successHandler;
//        authManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsRepository());
//        successHandler = new BasicAuthenticationSuccessHandler();
//        basicAuthenticationFilter = new AuthenticationWebFilter(authManager);
//        basicAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
//        return basicAuthenticationFilter;
//    }
//    private AuthenticationWebFilter bearerAuthenticationFilter() {
//        AuthenticationWebFilter bearerAuthenticationFilter;
//        Function<ServerWebExchange, Mono<Authentication>> bearerConverter;
//        ReactiveAuthenticationManager authManager;
//        authManager = new BearerTokenReactiveAuthenticationManager();
//        bearerAuthenticationFilter = new AuthenticationWebFilter(authManager);
//        bearerConverter = new ServerHttpBearerAuthenticationConverter();
//        bearerAuthenticationFilter.setAuthenticationConverter(bearerConverter);
//        bearerAuthenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/api/**"));
//        return bearerAuthenticationFilter;
//    }
//    @Bean
//    public ReactiveAuthenticationManager bearerAuthenticationManager() {
//        return new BearerTokenReactiveAuthenticationManager();
//    }
}