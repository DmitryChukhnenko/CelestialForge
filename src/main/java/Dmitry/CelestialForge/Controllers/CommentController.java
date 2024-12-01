package Dmitry.CelestialForge.Controllers;

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
import Dmitry.CelestialForge.Services.LikeService;
import Dmitry.CelestialForge.Session.SessionService;

@Controller
@RequestMapping("/blog/{blogPostId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private BlogPostService blogPostService;
    @Autowired
    private LikeService likeService;

    @GetMapping("/")
    public String listComments(@PathVariable Long blogPostId, Model model) {
        BlogPost blogPost = blogPostService.findById(blogPostId);
        // TODO: Подумай о кешировании блог поста
        model.addAttribute("comments", commentService.findByBlogPost(blogPost));
        return "comment/list";
    }

    @GetMapping("/create")
    public String createCommentForm(@PathVariable Long blogPostId, Model model) {
        model.addAttribute("blogPost", blogPostService.findById(blogPostId));
        return "comment/create";
    }

    @PostMapping("/create")
    public String createBlogPost(@ModelAttribute BlogPost blogPost) {
        blogPost.setUser(sessionService.getUser());
        blogPostService.addBlogPost(blogPost);
        return "redirect:/comments/";
    }

    @PostMapping("/like")
    public String likeComment(@ModelAttribute Comment comment) {
        likeService.addLikeToComment(sessionService.getUser(), comment);
        return "redirect:/comments/";
    }
}
