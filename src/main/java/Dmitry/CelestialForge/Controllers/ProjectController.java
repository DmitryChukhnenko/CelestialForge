package Dmitry.CelestialForge.Controllers;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Services.DonationService;
import Dmitry.CelestialForge.Services.FileStorageService;
import Dmitry.CelestialForge.Services.ProjectLikeService;
import Dmitry.CelestialForge.Services.ProjectService;
import Dmitry.CelestialForge.Session.SessionService;
import io.minio.errors.MinioException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private DonationService donationService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ProjectLikeService likeService;

    @GetMapping
    public String listProjects(Model model) {
        List<Project> projects = projectService.findByUser(sessionService.getUser());
        model.addAttribute("projects", projects);
        return "project/list";
    }

    @GetMapping("/create")
    public String createProjectForm(Model model) {
        model.addAttribute("project", new Project());
        return "project/create";
    }

    @PostMapping("/create")
    public String createProject(@Valid @ModelAttribute Project project,
                                 @RequestParam("image") MultipartFile image, Model model) {

        if (!image.isEmpty()) {
            try {                
                String imageUrl = fileStorageService.uploadFile(image, "blog", project.getId());
                project.setPictureUrl(imageUrl);                
            } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                model.addAttribute("errorMessage", "Ошибка при загрузке изображения.");
                return "blog/create";
            }
        }
        User user = sessionService.getUser();
        project.setUser(user);
        project.getContributors().add(user);
        projectService.addProject(project);
        return "redirect:/project/" + project.getId();
    }

    @GetMapping("/{id}")
    public String viewProject(@PathVariable Long id, Model model) {
        Project project = projectService.findWithAll(id);
        if (project != null) {
            model.addAttribute("project", project);
            User user = sessionService.getUser();
            
            if(user != null) {
                model.addAttribute("isOwnerOrContributor", projectService.isOwnerOrContributor(project, user));
                model.addAttribute("userLiked", likeService.hasUserLiked(user, project));
            }

            return "project/view";            
        }
        return "redirect:/project";
    }

    @GetMapping("/{id}/donate")
    public String donateToProjectForm(@PathVariable Long id, Model model) {
        Project project = projectService.findWithAll(id);
        if (project != null) {
            model.addAttribute("project", project);
            return "project/donate";        
        }
        return "redirect:/project";        
    }

    @PostMapping("/{id}/donate")
    public String donateToProject(@PathVariable Long id, @RequestParam Double amount) {
        donationService.donateToProject(sessionService.getUser(), id, amount);
        return "redirect:/project/" + id;
    }
}
