package Dmitry.CelestialForge.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.Comment;
import Dmitry.CelestialForge.Entities.Like;
import Dmitry.CelestialForge.Entities.User;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUser(User user);
    List<Like> findByBlogPost(BlogPost blogPost);
    List<Like> findByComment(Comment comment);
}
