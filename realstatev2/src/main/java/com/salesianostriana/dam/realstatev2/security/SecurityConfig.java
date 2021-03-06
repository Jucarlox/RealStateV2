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
                .antMatchers(HttpMethod.POST, "/auth/register/user").anonymous()
                .antMatchers(HttpMethod.POST, "/auth/login").anonymous()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST,"/auth/register/gestor").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/auth/register/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/propietario/").authenticated()
                .antMatchers(HttpMethod.GET,"/propietario/**").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.DELETE,"/propietario/**").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.POST,"/vivienda/").hasRole("PROPIETARIO")
                .antMatchers(HttpMethod.GET,"/vivienda/").authenticated()
                .antMatchers(HttpMethod.GET,"/vivienda/**").authenticated()
                .antMatchers(HttpMethod.PUT,"/vivienda/**").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.DELETE,"/vivienda/**").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.POST, "vivienda/**/inmobiliaria/**").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.DELETE, "vivienda/**/inmobiliaria").hasAnyRole("ADMIN", "PROPIETARIO", "GESTOR")
                .antMatchers(HttpMethod.POST,"/inmobiliaria/").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/inmobiliaria/**/gestor").hasAnyRole("ADMIN", "GESTOR")
                .antMatchers(HttpMethod.DELETE,"/inmobiliaria/gestor/**").hasAnyRole("ADMIN", "GESTOR")
                .antMatchers(HttpMethod.GET,"/inmobiliaria/**/gestor").hasAnyRole("ADMIN", "GESTOR")
                .antMatchers(HttpMethod.GET,"/inmobiliaria/").authenticated()
                .antMatchers(HttpMethod.GET,"/inmobiliaria/**").authenticated()
                .antMatchers(HttpMethod.DELETE,"/inmobiliaria/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/vivienda/**/meinteresa").hasRole("PROPIETARIO")
                .antMatchers(HttpMethod.POST, "/vivienda/**/meinteresa").hasAnyRole("PROPIETARIO", "ADMIN")
                .antMatchers(HttpMethod.GET, "/interesado/").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/interesado/**").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.GET, "vivienda/top").authenticated()
                .antMatchers(HttpMethod.GET, "/viviendas/propietario").hasRole("PROPIETARIO")
                .antMatchers(HttpMethod.GET, "/viviendas/interesa").authenticated()
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
