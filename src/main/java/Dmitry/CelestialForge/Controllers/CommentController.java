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
import org.springframework.web.bind.annotation.RequestParam;

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.Comment;
import Dmitry.CelestialForge.Services.BlogPostService;
import Dmitry.CelestialForge.Services.CommentService;
import Dmitry.CelestialForge.Services.LikeService;
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
    @Autowired
    private LikeService likeService;

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

    @PostMapping("/like")
    public String likeComment(@PathVariable Long projectId, @PathVariable Long blogPostId, @RequestParam Long commentId) {
        Comment comment = commentService.findById(commentId);
        if (comment != null) {
            likeService.addLikeToComment(sessionService.getUser(), comment);
            BlogPost blogPost = comment.getBlogPost();
            if (blogPost != null) 
                return "redirect:/project/" + projectId + "/blog/" + blogPostId;
        }
        return "redirect:";
    }
}

