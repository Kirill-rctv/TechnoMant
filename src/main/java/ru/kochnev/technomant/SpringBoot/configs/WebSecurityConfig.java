package ru.kochnev.technomant.SpringBoot.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kochnev.technomant.SpringBoot.services.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider(userService));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/registration").not().fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/article/**").hasAnyRole("ADMIN", "WRITER")
                .antMatchers(HttpMethod.PUT, "/user/**", "/article/**").hasAnyRole("ADMIN", "WRITER")
                .antMatchers(HttpMethod.PATCH, "/article/**").hasAnyRole("ADMIN", "WRITER")
                .antMatchers(HttpMethod.GET, "/user/**", "/article/**").hasAnyRole("ADMIN", "WRITER")
                .antMatchers(HttpMethod.GET, "/statistic/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/user/**", "/article/**").hasAnyRole("ADMIN", "WRITER")
                .antMatchers("/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().sessionManagement().disable();
    }
}