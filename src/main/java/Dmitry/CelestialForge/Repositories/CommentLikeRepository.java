package Dmitry.CelestialForge.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

import Dmitry.CelestialForge.Entities.Comment;
import Dmitry.CelestialForge.Entities.CommentLike;
import Dmitry.CelestialForge.Entities.User;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    List<CommentLike> findByUser(User user);
    List<CommentLike> findByComment(Comment comment);
    List<CommentLike> findByUserAndComment(User user, Comment comment);
    boolean existsByUserAndComment(User user, Comment comment);    
    int countByComment(Comment comment);

    @Query("SELECT pl.comment.id FROM CommentLike pl WHERE pl.user = :user AND pl.comment IN :comments")
    List<Long> findLikedCommentIds(@Param("user") User user, @Param("comments") List<Comment> comments);

    @Query("SELECT cl.comment.id FROM CommentLike cl WHERE cl.user.id = :userId AND cl.comment.id IN :commentIds")
    Set<Long> findLikedCommentIdsByUserAndCommentIds(
        @Param("userId") Long userId,
        @Param("commentIds") Set<Long> commentIds
    );
}
