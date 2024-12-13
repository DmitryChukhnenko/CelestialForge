package Dmitry.CelestialForge.Controllers;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Services.FileStorageService;
import Dmitry.CelestialForge.Services.ProjectService;
import Dmitry.CelestialForge.Services.UserService;
import io.minio.errors.MinioException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ProjectService projectService; 

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/login?logout";
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "index";
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Неверное имя пользователя или пароль!");
        }
        if (logout != null) {
            model.addAttribute("successMessage", "Вы успешно вышли из системы.");
        }
        return "login";
    }
    
    @GetMapping("/registration")
    public String registration(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(Model model, 
                               @Valid @ModelAttribute User user, 
                               BindingResult bindingResult,
                               @RequestParam("image") MultipartFile image) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (!image.isEmpty()) {
            try {
                String imageUrl = fileStorageService.uploadFile(image, "user", user.getId());
                user.setPictureUrl(imageUrl);
                userService.addUser(user);
            } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                model.addAttribute("errorMessage", "Ошибка при загрузке изображения.");
                return "registration";
            }
        }
        else userService.addUser(user);

        return "redirect:/login";
    }
}

