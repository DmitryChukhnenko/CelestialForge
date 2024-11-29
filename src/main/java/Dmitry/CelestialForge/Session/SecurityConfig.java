package Dmitry.CelestialForge.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import Dmitry.CelestialForge.Services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        UserDetails user = User.builder()
                               .username("user")
                               .password(encoder.encode("password"))
                               .roles("USER")
                               .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomUserDetailsService customUserDetailsService) throws Exception {
        http
            .sessionManagement(session -> session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
            )
            .headers(headers -> headers
                .httpStrictTransportSecurity(Customizer.withDefaults())
            )
            .sessionManagement(session -> session
                .sessionFixation().migrateSession()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .enableSessionUrlRewriting(false)
                .invalidSessionUrl("/session-expired")
                .maximumSessions(1)
            )
            .authorizeHttpRequests((authz) -> authz
            	.requestMatchers("/registration", "/login", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login")           // Custom login page
                    .loginProcessingUrl("/login")  // URL to submit the login form
                    .usernameParameter("email")    // Parameter name for username
                    .passwordParameter("password") // Parameter name for password
                    .defaultSuccessUrl("/", true)  // Redirect after successful login
                    .failureUrl("/login?error=true") // Redirect after failed login
                )
            .logout(Customizer.withDefaults())
        	.userDetailsService(customUserDetailsService);
        return http.build();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}