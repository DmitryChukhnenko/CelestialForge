package Dmitry.CelestialForge.Controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.Comment;
import Dmitry.CelestialForge.Services.BlogPostService;
import Dmitry.CelestialForge.Services.CommentService;
import Dmitry.CelestialForge.Session.SessionService;

@Controller
@RequestMapping("/project/{projectId}/blog/{blogPostId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private BlogPostService blogPostService;

    @GetMapping("/create")
    public String createCommentForm(@PathVariable Long projectId, @PathVariable Long blogPostId, Model model) {
        model.addAttribute("blogPost", blogPostService.findById(blogPostId));
        model.addAttribute("projectId", projectId);
        model.addAttribute("comment", new Comment());
        return "comment/create";
    }

    @PostMapping("/create")
    public String createComment(@PathVariable Long projectId, @PathVariable Long blogPostId, @ModelAttribute Comment comment) {
        BlogPost blogPost = blogPostService.findById(blogPostId);
        comment.setUser(sessionService.getUser());
        comment.setBlogPost(blogPost);
        comment.setCreatedAt(LocalDateTime.now());
        commentService.addComment(comment);
        return "redirect:/project/" + projectId + "/blog/" + blogPostId;
    }
}

