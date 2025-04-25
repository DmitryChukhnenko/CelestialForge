package Dmitry.CelestialForge.Controllers;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

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

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.Comment;
import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Services.BlogPostLikeService;
import Dmitry.CelestialForge.Services.BlogPostService;
import Dmitry.CelestialForge.Services.CommentLikeService;
import Dmitry.CelestialForge.Services.FileStorageService;
import Dmitry.CelestialForge.Services.ProjectService;
import Dmitry.CelestialForge.Session.SessionService;
import io.minio.errors.MinioException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/project/{projectId}/blog")
public class BlogController {

    @Autowired
    private BlogPostService blogPostService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private BlogPostLikeService blogPostLikeService;
    @Autowired
    private CommentLikeService commentLikeService;
    @Autowired 
    private SessionService sessionService;
    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public String listBlogPosts(@PathVariable Long projectId, Model model) {
        Project project = projectService.findWithAll(projectId); 
        List<BlogPost> blogPosts = blogPostService.findByProject(project);
        model.addAttribute("blogPosts", blogPosts);
        model.addAttribute("projectId", projectId);
        model.addAttribute("isOwnerOrContributor", projectService.isOwnerOrContributor(project, sessionService.getUser()));
        return "blog/list";
    }

    @GetMapping("/create")
    public String createBlogPostForm(@PathVariable Long projectId, Model model) {
        Project project = projectService.findWithAll(projectId);
        if (!projectService.isOwnerOrContributor(project, sessionService.getUser())) {
            return "redirect:/project/" + projectId + "/blog";
        }
        model.addAttribute("project", project);
        model.addAttribute("blogPost", new BlogPost());
        return "blog/create";
    }

    @PostMapping("/create")
    public String createBlogPost(@PathVariable Long projectId, @Valid @ModelAttribute BlogPost blogPost,
                                 @RequestParam("image") MultipartFile image, Model model) {
        Project project = projectService.findWithAll(projectId);
        if (!projectService.isOwnerOrContributor(project, sessionService.getUser())) {
            return "redirect:/project/" + projectId + "/blog";
        }
        if (!image.isEmpty()) {
            try {                
                String imageUrl = fileStorageService.uploadFile(image, "blog", blogPost.getId());
                blogPost.setPictureUrl(imageUrl);                
            } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                model.addAttribute("errorMessage", "Ошибка при загрузке изображения.");
                return "blog/create";
            }
        }

        blogPost.setUser(sessionService.getUser());
        blogPost.setProject(project); 
        blogPostService.addBlogPost(blogPost);
        project.setBlogPost(blogPost); 
        projectService.addProject(project); 
        return "redirect:/project/" + projectId + "/blog/" + blogPost.getId();
    }

    @GetMapping("/{id}")
    public String viewBlogPost(@PathVariable Long projectId, @PathVariable Long id, Model model) {
        Project project = projectService.findWithAll(projectId);
        BlogPost blogPost = blogPostService.findById(id);
        
        if (blogPost == null || !blogPost.getProject().getId().equals(projectId)) {
            throw new EntityNotFoundException("Пост в блоге не найден");
        }
        User user = sessionService.getUser();

        model.addAttribute("project", project);
        model.addAttribute("blogPost", blogPost);
        model.addAttribute("isOwnerOrContributor", projectService.isOwnerOrContributor(project, sessionService.getUser()));        
        
        if(user != null) {
            model.addAttribute("blogPostLiked", blogPostLikeService.hasUserLiked(user, blogPost));
            Set<Comment> comments = blogPost.getComments();
            Set<Long> likedComments = commentLikeService.findLikedCommentIds(user, comments);
            comments.forEach(c -> c.setLikedByCurrentUser(likedComments.contains(c.getId())));
        }
        
        return "blog/view";
    }
}


    