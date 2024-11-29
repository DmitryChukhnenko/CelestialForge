package Dmitry.CelestialForge.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Services.UserService;


@Service
public class SessionService {
    @Autowired
    private UserService userService;

    public UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();     
    }

    public User getUser() {
        return userService.findByEmail(getUserDetails().getUsername());
    }
}