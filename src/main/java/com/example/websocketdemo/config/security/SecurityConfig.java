package com.example.websocketdemo.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @EnableGlobalAuthentication annotates:
 * @EnableWebSecurity
 * @EnableWebMvcSecurity
 * @EnableGlobalMethodSecurity Passing in 'prePostEnabled = true' allows:
 * <p>
 * Pre/Post annotations such as:
 * @PreAuthorize("hasRole('ROLE_USER')")
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;
    private final AccessDeniedHandler accessDeniedHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final AuthenticationSuccessHandler customLoginSuccessHandler;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider bean = new CustomDaoAuthenticationProvider();
        bean.setUserDetailsService(customUserDetailsService);
        return bean;
    }

    /**
     * Order of precedence is very important.
     * <p>
     * Matching occurs from top to bottom - so, the topmost match succeeds first.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index", "/authenticate", "/alert")
                .permitAll()
                .antMatchers("/secured/socket")
                .authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/authenticate")
                .successHandler(customLoginSuccessHandler)
                .failureUrl("/denied").permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                /**
                 * Applies to User Roles - not to login failures or unauthenticated access attempts.
                 */
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .authenticationProvider(authenticationProvider());

        /** Disabled for local testing */
        http
                .csrf().disable();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
}
