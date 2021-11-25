package com.salesianostriana.dam.realstatev2.security;

import com.salesianostriana.dam.realstatev2.security.jwt.JwtAccessDeniedHandler;
import com.salesianostriana.dam.realstatev2.security.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthorizationFilter filter;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                //.antMatchers(HttpMethod.POST, "/vivienda/**").hasRole("PROPIETARIO")
                .antMatchers(HttpMethod.POST, "/auth/register/user").anonymous()
                .antMatchers(HttpMethod.POST, "/auth/login").anonymous()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST,"/auth/register/gestor").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/auth/register/admin").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/propietario/").authenticated()
                .antMatchers(HttpMethod.GET,"/propietario/{id}").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.DELETE,"/propietario/{id}").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.POST,"/vivienda/").hasRole("PROPIETARIO")
                .antMatchers(HttpMethod.GET,"/vivienda/").authenticated()
                .antMatchers(HttpMethod.GET,"/vivienda/{id}").authenticated()
                .antMatchers(HttpMethod.PUT,"/vivienda/{id}").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.DELETE,"/vivienda/{id}").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.POST, "vivienda/{id}/inmobiliaria/{id2}").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.DELETE, "vivienda/{id}/inmobiliaria").hasAnyRole("ADMIN", "PROPIETARIO", "GESTOR")
                .antMatchers(HttpMethod.POST,"/inmobiliaria/").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/inmobiliaria/{id}/gestor").hasAnyRole("ADMIN", "GESTOR")
                .antMatchers(HttpMethod.DELETE,"/inmobiliaria/gestor/{id}").hasAnyRole("ADMIN", "GESTOR")
                .antMatchers(HttpMethod.GET,"/inmobiliaria/{id}/gestor").hasAnyRole("ADMIN", "GESTOR")
                .antMatchers(HttpMethod.GET,"/inmobiliaria/").authenticated()
                .antMatchers(HttpMethod.GET,"/inmobiliaria/{id}").authenticated()
                .antMatchers(HttpMethod.DELETE,"/inmobiliaria/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/vivienda/{id}/meinteresa").hasRole("PROPIETARIO")
                .antMatchers(HttpMethod.POST, "/vivienda/{id}/meinteresa").hasAnyRole("PROPIETARIO", "ADMIN")
                .antMatchers(HttpMethod.GET, "/interesado/").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/interesado/{id}").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.GET, "vivienda/top?n=5").authenticated()
                .anyRequest().authenticated();
        
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().disable();

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }






}
