package Dmitry.CelestialForge.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.Like;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Services.BlogPostService;
import Dmitry.CelestialForge.Services.LikeService;
import Dmitry.CelestialForge.Session.SessionService;

@RestController
@RequestMapping("/blog-posts/{blogPostId}/likes")
public class BlogLikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private BlogPostService blogPostService;
        
    @PostMapping("/like")
    public ResponseEntity<Like> likeBlogPost(@PathVariable Long blogPostId) {
        User user = sessionService.getUser();
        BlogPost blogPost = blogPostService.findById(blogPostId);
        Like like = likeService.addLikeToBlogPost(user, blogPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(like);
    }

    @GetMapping("/likes")
    public long getLikesForBlogPost(@PathVariable Long blogPostId) {
        BlogPost blogPost = blogPostService.findById(blogPostId);
        return likeService.countLikesForBlogPost(blogPost);
    }
}

