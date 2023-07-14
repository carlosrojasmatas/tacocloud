package com.sia.tacocloud.configuration;

import com.sia.tacocloud.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetails(PasswordEncoder encoder,
                                          UserRepository userRepository) {
        return username -> {
            var user = userRepository.findByUsername(username);
            if (user != null) {
                return user;
            }

            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(req ->
                req
                        .requestMatchers("/taco/gConfig").permitAll()
                        .requestMatchers("/taco/**", "/order/**")
                        .authenticated()
                        .requestMatchers("/", "/**").permitAll());
        http.oauth2Login(login -> login.loginPage("/login"));

        http.formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/taco/design"));

        http.csrf(Customizer.withDefaults());
        http.logout(logout -> logout.logoutSuccessUrl("/"));
        return http.build();
    }
}
