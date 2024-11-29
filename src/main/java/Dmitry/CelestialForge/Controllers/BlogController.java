package Dmitry.CelestialForge.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Services.BlogPostService;
import Dmitry.CelestialForge.Services.ProjectService;

@RestController
@RequestMapping("/projects/{projectId}/blog")
public class BlogController {

    @Autowired
    private BlogPostService blogPostService;
    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public BlogPost createBlogPost(@PathVariable Long projectId, @RequestBody BlogPost blogPost) {
        Project project = projectService.findById(projectId);
        blogPost.setProject(project);
        return blogPostService.addBlogPost(blogPost);
    }

    @GetMapping("/get")
    public List<BlogPost> getBlogPosts(@PathVariable Long projectId) {
        Project project = projectService.findById(projectId);
        return blogPostService.findByProject(project);
    }
}

