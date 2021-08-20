package com.jose.chatprueba.security;

import java.util.Arrays;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jose.chatprueba.security.jwt.JwtAuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	   private final UserDetailsService userDetailsService;
	    private final PasswordEncoder passwordEncoder;
	    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
	    @Lazy
	    private final JwtAuthorizationFilter jwtAuthorizationFilter;

	    @Bean
	    public SessionRegistry sessionRegistry() {
	        return new SessionRegistryImpl();
	    }

	    @Bean
	    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
	        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	    }

	    @Bean
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }

	    //Configuracion del CORS

	    @Bean
	    CorsConfigurationSource corsConfigurationSource(){
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowCredentials(true);
	        configuration.setAllowedOriginPatterns(
	                Arrays.asList("http://localhost:[*]","chrome-extension://**","file://**"));
	        configuration.setAllowedMethods(
	                Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
	        configuration.setAllowedHeaders(
	                Arrays.asList(
	                        "Authorization",    
	                        "Cache-Control",
	                        "Content-Type",
	                        "Access-Control-Allow-Origin",
	                        "Access-Control-Allow-Credentials"
	                ));

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }

	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	    }

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	                .cors()
	                .and()
	                .csrf().disable() //Desactivar y testear en versiones finales
	                .exceptionHandling()
	                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
	                .and()
	                .sessionManagement()
	                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
	                    //.maximumSessions(1)
	                    //.sessionRegistry(sessionRegistry())
	                //.and()
	                .and()
	                .authorizeRequests()
	                    .antMatchers(HttpMethod.GET, "/chat").permitAll()
	                    .antMatchers(HttpMethod.GET, "/chat/**").permitAll()
	                    .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
	                    .antMatchers(HttpMethod.GET, "/usuario/**").hasRole("USER")
	                    .antMatchers(HttpMethod.POST, "/usuario/**").hasRole("ADMIN")
	                    .antMatchers(HttpMethod.PUT, "/usuario/**").hasRole("ADMIN")
	                    .antMatchers(HttpMethod.DELETE, "/usuario/**").hasRole("ADMIN")
	                    .antMatchers(HttpMethod.GET, "/user/**").hasRole("USER")
	                    .anyRequest().authenticated()
	                .and()
	                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
	    }
	}