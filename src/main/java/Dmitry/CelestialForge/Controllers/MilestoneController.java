package Dmitry.CelestialForge.Controllers;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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

import Dmitry.CelestialForge.Entities.Milestone;
import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Services.FileStorageService;
import Dmitry.CelestialForge.Services.MilestoneService;
import Dmitry.CelestialForge.Services.ProjectService;
import Dmitry.CelestialForge.Session.SessionService;
import io.minio.errors.MinioException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/project/{projectId}/milestone")
public class MilestoneController {

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/create")
    public String createMilestoneForm(@PathVariable Long projectId, Model model) {
        Project project = projectService.findWithAll(projectId);
        if (!projectService.isOwnerOrContributor(project, sessionService.getUser())) {
            return "redirect:/project/" + projectId;
        }
        model.addAttribute("project", project);
        model.addAttribute("milestone", new Milestone());
        return "milestone/create";
    }

    @PostMapping("/create")
    public String createMilestone(@PathVariable Long projectId, @Valid @ModelAttribute Milestone milestone,
                                  @RequestParam("image") MultipartFile image, Model model) {
        Project project = projectService.findWithAll(projectId);
        if (!projectService.isOwnerOrContributor(project, sessionService.getUser())) {
            return "redirect:/project/" + projectId;
        }

        if (!image.isEmpty()) {
            try {
                String imageUrl = fileStorageService.uploadFile(image, "milestone", milestone.getId());
                milestone.setPictureUrl(imageUrl);
            } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                model.addAttribute("errorMessage", "Ошибка при загрузке изображения.");
                return "milestone/create";
            }
        }

        milestone.setProject(project);
        milestoneService.addMilestone(milestone);
        project.setMilestone(milestone); // Обновление связи
        projectService.addProject(project); // Сохранение проекта с новым Milestone
        return "redirect:/project/" + projectId;
    }

    @GetMapping("/{id}")
    public String viewMilestone(@PathVariable Long projectId, @PathVariable Long id, Model model) {
        Project project = projectService.findWithAll(projectId);
        Milestone milestone = milestoneService.findById(id);
        if (milestone != null && milestone.getProject().getId().equals(projectId)) {
            model.addAttribute("milestone", milestone);
            model.addAttribute("isOwnerOrContributor", projectService.isOwnerOrContributor(project, sessionService.getUser()));
            return "milestone/view";
        }
        return "redirect:/project/" + projectId;
    }
}



