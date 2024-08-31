package ru.slavich.project.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.slavich.project.services.UserService;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfiguration {

    private final UserService userService;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/", "/documentsview").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/insertcategoryparent").hasRole("ADMIN")
                        .requestMatchers("/inserttypedocument").hasRole("ADMIN")
                        .requestMatchers("/insertcategorychild").hasRole("ADMIN")
                        .requestMatchers("/insertdocument").hasRole("ADMIN")
                        .requestMatchers("/deletedocument").hasRole("ADMIN")
                        .requestMatchers("/deleteparent").hasRole("ADMIN")
                        .requestMatchers("/deletechild").hasRole("ADMIN")
                        .requestMatchers("/{catid}", "/css/**", "/js/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
}
