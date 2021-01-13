package com.sovback.sovback.security;


import com.sovback.sovback.security.jwt.AuthTokenFilter;
import com.sovback.sovback.security.jwt.JwtAuthEntryPoint;
import com.sovback.sovback.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@EnableWebMvc
//@EnableWebflux
@Configuration
public class TestWebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Bean(name = "mvcHandlerMappingIntrospector")
//    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
//        return new HandlerMappingIntrospector();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/news/**").permitAll()
                .antMatchers("/api/accounts/**").permitAll()
                .antMatchers("/api/notifications/**").permitAll()
                .antMatchers("/api/mail/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers("/api/user/**").permitAll()
                .anyRequest().authenticated();
    }
}
