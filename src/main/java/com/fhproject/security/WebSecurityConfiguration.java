package com.fhproject.security;

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import com.fhproject.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService customUserDetailService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public WebSecurityConfiguration(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return this.customUserDetailService;
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter()
            throws Exception {
        JsonUsernamePasswordAuthenticationFilter authenticationFilter
                = new JsonUsernamePasswordAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
        .and() // "/home" accessible by everybody
                .authorizeRequests()
                .antMatchers("/login", "/logout","/cart","/user","/product/admin/getAllProducts","/product/*","/product/getAllProducts", "/product/admin/getAllProducts", "/product/getSpecificProduct/*","/product/getAllProductsOfCategory/*","/category/getAllCategories","/user/registration")
                //.antMatchers("/")
                .permitAll()
        .and() // "/admin" accessible by user with ROLE_ADMIN
                .authorizeRequests()
                .antMatchers("/admin","/products/new")
                .access("hasRole('admin')")
        .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                })
        .and() // lock every route
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable();
        http
                .addFilterAt(
                        usernamePasswordAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );
        http
                .exceptionHandling()
                .accessDeniedHandler(
                        (httpServletRequest, httpServletResponse, e) ->
                                httpServletResponse.sendError(
                                        HttpServletResponse.SC_FORBIDDEN
                                )
                )
                .authenticationEntryPoint(
                        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                );

        /*http // "/home" accessible by everybody
             .authorizeRequests()
             .antMatchers("/")
             .permitAll();
       /* http // "/user" accessible by user with admin
             .authorizeRequests()
             .antMatchers("/user")
             .access("hasRole('admin')");
      /*  http // "/user" accessible by user with admin
                .authorizeRequests()
                .antMatchers("/admin")
                .access("hasRole('admin')");
        http // "/admin" accessible by user with ROLE_ADMIN
                .authorizeRequests()
                .antMatchers("/cart")
                .access("hasRole('user')");
        /*http // lock every route
             .authorizeRequests()
             .anyRequest()
             .authenticated();
        http
            .csrf()
            .disable();
        http
            .formLogin()
            .loginProcessingUrl("/login")
            .permitAll();

        http
            .logout()
            .logoutUrl("/logout")
            //.permitAll();
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                });

        http
            .addFilterAt(
                usernamePasswordAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class
            );
        http
            .exceptionHandling()
            .accessDeniedHandler(
                (httpServletRequest, httpServletResponse, e) ->
                    httpServletResponse.sendError(
                        HttpServletResponse.SC_FORBIDDEN
                    )
            )
            .authenticationEntryPoint(
                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
            );
        http
                .cors();*/
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:63342"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}