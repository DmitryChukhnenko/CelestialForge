package Dmitry.CelestialForge.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.Comment;
import Dmitry.CelestialForge.Entities.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUser(User user);
    List<Comment> findByBlogPost(BlogPost blogPost);
}
