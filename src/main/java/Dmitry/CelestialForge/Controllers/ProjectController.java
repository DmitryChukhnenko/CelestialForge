package Dmitry.CelestialForge.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Services.DonationService;
import Dmitry.CelestialForge.Services.MilestoneService;
import Dmitry.CelestialForge.Services.ProjectService;
import Dmitry.CelestialForge.Session.SessionService;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private DonationService donationService;
    @Autowired
    private MilestoneService milestoneService;
    @Autowired
    private SessionService sessionService;

    @GetMapping("/")
    public String listProjects(Model model) {
        List<Project> projects = projectService.findByUser(sessionService.getUser());
        model.addAttribute("projects", projects);
        return "project/list";
    }

    @GetMapping("/create")
    public String createProjectForm(Model model) {
        return "project/create";
    }

    @PostMapping("/create")
    public String createProject(@ModelAttribute Project project) {
        project.setUser(sessionService.getUser());
        projectService.addProject(project);
        return "redirect:/project/";
    }

    @GetMapping("/{id}")
    public String viewProject(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id);
        // TODO: Вот тут кешируем проект
        model.addAttribute("project", project);
        model.addAttribute("milestones", milestoneService.findByProject(project));
        return "project/view";
    }

    @GetMapping("/{id}/donate")
    public String donateToProjectForm(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        return "project/view/donate";
    }

    @PostMapping("/{id}/donate")
    public String donateToProject(@PathVariable Long id, @ModelAttribute Double amount) {
        donationService.donateToProject(sessionService.getUser(), projectService.findById(id), amount);
        return "redirect:/project/view";
    }
}

