package de.sharetrip.core.config;

import de.sharetrip.core.security.TokenAuthenticationFilter;
import de.sharetrip.core.security.TokenProvider;
import de.sharetrip.core.security.user.repository.CustomUserDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    private final CustomUserDetailsRepository customUserDetailsRepository;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        final TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter(
                tokenProvider,
                customUserDetailsRepository);

        http.cors()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf()
            .disable()
            .formLogin()
            .disable()
            .httpBasic()
            .disable()
            .authorizeRequests()
            .antMatchers("/api/**")
            .authenticated()
            .and()
            .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring()
           .antMatchers(HttpMethod.POST, "/oauth2/authorize")
           .antMatchers(HttpMethod.POST, "/api/user-management/user")
           .antMatchers(HttpMethod.GET, "/actuator/health")
           .antMatchers(HttpMethod.GET, "/")
           .antMatchers(HttpMethod.GET, "/csrf");
    }
}
