package Dmitry.CelestialForge.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Services.UserService;


@Service
public class SessionService {
    @Autowired
    private UserService userService;

    public User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        String email = auth.getName(); // Убедитесь, что auth.getName() возвращает email
        return userService.findByEmail(email);
    }
}