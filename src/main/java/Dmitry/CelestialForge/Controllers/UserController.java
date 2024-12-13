package Dmitry.CelestialForge.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Services.UserService;
import Dmitry.CelestialForge.Session.SessionService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;

    @GetMapping
    public String viewUser(Model model) {
        User user = sessionService.getUser();
        if (user != null) {
            model.addAttribute("user", user);
            return "user/view";
        }
        return "redirect:/";        
    }
    
    @GetMapping("/edit")
    public String editUser(Model model) {
        User user = sessionService.getUser();
        if (user != null) {
            model.addAttribute("user", new User(user.getId(), user.getPictureUrl()));
            return "user/edit";
        }
        return "redirect:/";
    }

    @PostMapping("/edit")
    public String updateUser(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        userService.updateUser(sessionService.getUser(), user);
        return "redirect:/user";
    }
}
