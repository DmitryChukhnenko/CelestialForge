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

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Services.BlogPostService;
import Dmitry.CelestialForge.Services.ProjectService;
import Dmitry.CelestialForge.Session.SessionService;

@Controller
@RequestMapping("/project/{projectId}/blog")
public class BlogController {

    @Autowired
    private BlogPostService blogPostService;
    @Autowired
    private ProjectService projectService;
    @Autowired 
    private SessionService sessionService;

    @GetMapping("/")
    public String listBlogPosts(@PathVariable Long projectId, Model model) {
        Project project = projectService.findById(projectId); // Повторяется
        // TODO: Подумай о кешировании проекта
        List<BlogPost> blogPosts = blogPostService.findByProject(project);
        model.addAttribute("blogPosts", blogPosts);
        return "blog/list";
    }

    @GetMapping("/create")
    public String createBlogPostForm(@PathVariable Long projectId, Model model) {
        model.addAttribute("project", projectService.findById(projectId)); // Несколько
        return "blog/create";
    }

    @PostMapping("/create")
    public String createBlogPost(@PathVariable Long projectId, @ModelAttribute BlogPost blogPost) {
        blogPost.setUser(sessionService.getUser());
        blogPost.setProject(projectService.findById(projectId)); // Раз
        blogPostService.addBlogPost(blogPost);
        return "redirect:/blog/";
    }

    @GetMapping("/{id}")
    public String viewBlogPost(@PathVariable Long id, Model model) {
        BlogPost blogPost = blogPostService.findById(id);
        model.addAttribute("blogPost", blogPost);
        return "blog/view";
    }
}

