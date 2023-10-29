package simbir.go.simbir_go.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(antMatcher(HttpMethod.POST, "/api/Account/SignIn")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.POST, "/api/Account/SignUp")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.GET, "/api/Rent/Transport")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.GET, "/api/Transport/{id}")).permitAll()
                                .requestMatchers("/v3/api-docs",
                                        "/v3/**",
                                        "/configuration/ui",
                                        "/swagger-resources/**",
                                        "/configuration/**",
                                        "/swagger-ui.html",
                                        "/webjars/**",
                                        "/swagger-ui/**"
                                ).permitAll()
                                .requestMatchers("/api/Admin/**").hasRole("ADMIN")

                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
