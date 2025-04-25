package Dmitry.CelestialForge.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.BlogPostLike;
import Dmitry.CelestialForge.Entities.User;

public interface BlogPostLikeRepository extends JpaRepository<BlogPostLike, Long> {
    List<BlogPostLike> findByUser(User user);
    List<BlogPostLike> findByBlogPost(BlogPost blogPost);
    List<BlogPostLike> findByUserAndBlogPost(User user, BlogPost blogPost);
    boolean existsByUserAndBlogPost(User user, BlogPost blogPost);
    int countByBlogPost(BlogPost blogPost);

    @Query("SELECT pl.blogPost.id FROM BlogPostLike pl WHERE pl.user = :user AND pl.blogPost IN :blogPosts")
    List<Long> findLikedBlogPostIds(@Param("user") User user, @Param("blogPosts") List<BlogPost> blogPosts);
}