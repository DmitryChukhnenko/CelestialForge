package Dmitry.CelestialForge.Controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Dmitry.CelestialForge.Entities.ProjectLike;
import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.BlogPostLike;
import Dmitry.CelestialForge.Entities.Comment;
import Dmitry.CelestialForge.Entities.CommentLike;
import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Services.BlogPostLikeService;
import Dmitry.CelestialForge.Services.BlogPostService;
import Dmitry.CelestialForge.Services.CommentLikeService;
import Dmitry.CelestialForge.Services.CommentService;
import Dmitry.CelestialForge.Services.ProjectLikeService;
import Dmitry.CelestialForge.Services.ProjectService;
import Dmitry.CelestialForge.Session.SessionService;

@RestController
@RequestMapping("/api/likes")
public class LikeController {
    
    @Autowired
    private ProjectLikeService projectLikeService;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private BlogPostService blogPostService;
    @Autowired
    private BlogPostLikeService blogPostLikeService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentLikeService commentLikeService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/project/{projectId}")
    public ResponseEntity<Map<String, Object>> toggleProjectLike(
        @PathVariable Long projectId
    ) {
        Optional<Project> p = projectService.findById(projectId);        
        User user = sessionService.getUser();

        if (!p.isPresent()) return new ResponseEntity(HttpStatusCode.valueOf(500));
        Project project = p.get();
        ProjectLike result = projectLikeService.toggleLike(user, project);
        
        Map<String, Object> response = new HashMap<>();
        response.put("liked", result != null);
        response.put("count", projectLikeService.countLikes(project));
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/blogPost/{blogPostId}")
    public ResponseEntity<Map<String, Object>> toggleBlogPostLike(
        @PathVariable Long blogPostId
    ) {
        BlogPost blogPost = blogPostService.findById(blogPostId);        
        User user = sessionService.getUser();

        if (blogPost == null) return new ResponseEntity(HttpStatusCode.valueOf(500));
        BlogPostLike result = blogPostLikeService.toggleLike(user, blogPost);
        
        Map<String, Object> response = new HashMap<>();
        response.put("liked", result != null);
        response.put("count", blogPostLikeService.countLikes(blogPost));
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<Map<String, Object>> toggleCommentLike(
        @PathVariable Long commentId
    ) {
        Comment comment = commentService.findById(commentId);        
        User user = sessionService.getUser();

        if (comment == null) return new ResponseEntity(HttpStatusCode.valueOf(500));
        CommentLike result = commentLikeService.toggleLike(user, comment);
        
        Map<String, Object> response = new HashMap<>();
        response.put("liked", result != null);
        response.put("count", commentLikeService.countLikes(comment));
        
        return ResponseEntity.ok(response);
    }
}
