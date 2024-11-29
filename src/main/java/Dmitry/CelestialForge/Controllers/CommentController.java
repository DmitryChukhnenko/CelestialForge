package Dmitry.CelestialForge.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.Comment;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Services.BlogPostService;
import Dmitry.CelestialForge.Services.CommentService;
import Dmitry.CelestialForge.Session.SessionService;

@RestController
@RequestMapping("/blog-posts/{blogPostId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private BlogPostService blogPostService;

    @PostMapping
    public ResponseEntity<Comment> addComment(@PathVariable Long blogPostId, @RequestParam String content) {
        User user = sessionService.getUser();
        BlogPost blogPost = blogPostService.findById(blogPostId);
        Comment comment = commentService.addComment(blogPost, user, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @GetMapping
    public List<Comment> getComments(@PathVariable Long blogPostId) {
        BlogPost blogPost = blogPostService.findById(blogPostId);
        return commentService.findByBlogPost(blogPost);
    }
}
