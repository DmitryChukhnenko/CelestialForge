package Dmitry.CelestialForge.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Dmitry.CelestialForge.Entities.Comment;
import Dmitry.CelestialForge.Entities.Like;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Services.CommentService;
import Dmitry.CelestialForge.Services.LikeService;
import Dmitry.CelestialForge.Session.SessionService;

@RestController
@RequestMapping("/comments/{commentId}/likes")
public class CommentLikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private CommentService commentService;

    @PostMapping("/like")
    public ResponseEntity<Like> likeComment(@PathVariable Long commentId) {
        User user = sessionService.getUser();
        Comment comment = commentService.findById(commentId);
        Like like = likeService.addLikeToComment(user, comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(like);
    }

    @GetMapping("/likes")
    public long getLikesForComment(@PathVariable Long commentId) {
        Comment comment = commentService.findById(commentId);
        return likeService.countLikesForComment(comment);
    }
}
