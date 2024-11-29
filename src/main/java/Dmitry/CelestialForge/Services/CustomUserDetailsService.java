package Dmitry.CelestialForge.Services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService; // Assume this interacts with your DB
    @SuppressWarnings("unused")
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Dmitry.CelestialForge.Entities.User user = userService.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        return User.builder()
                   .username(user.getEmail()) 
                   .password(user.getPassword()) 
                   .build();
    }
}
