package Dmitry.CelestialForge.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Dmitry.CelestialForge.Entities.Comment;
import Dmitry.CelestialForge.Entities.CommentLike;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Repositories.CommentLikeRepository;

@Service
public class CommentLikeService {
    private static final String LIKES_COUNT_CACHE = "comment_likes_count";

    @Autowired
    private CommentLikeRepository likeRepository;

    @Transactional
    @CacheEvict(value = LIKES_COUNT_CACHE, key = "#comment.id")
    public CommentLike toggleLike(User user, Comment comment) {
        CommentLike existing = findConcrete(user, comment);
        if(existing != null) {
            likeRepository.delete(existing);
            return null;
        }
        
        CommentLike like = new CommentLike();
        like.setUser(user);
        like.setComment(comment);
        return likeRepository.save(like);
    }

    @Cacheable(value = LIKES_COUNT_CACHE, key = "#comment.id")
    public int countLikes(Comment comment) {
        return likeRepository.countByComment(comment);
    }

    public boolean hasUserLiked(User user, Comment comment) {
        return likeRepository.existsByUserAndComment(user, comment);
    }

    public CommentLike findConcrete(User user, Comment comment){
		List<CommentLike> likes = likeRepository.findByUserAndComment(user, comment);
		if(likes.size() > 0) return likes.get(0);
		return null;
	}

    public CommentLike findById(Long id){
		Optional<CommentLike> like = likeRepository.findById(id);
		if(like.isPresent()) return like.get();
		return null;
	}

    public Set<Long> findLikedCommentIds(User user, Set<Comment> comments) {
    if (user == null || comments.isEmpty()) {
        return Collections.emptySet();
    }
    
    Set<Long> commentIds = comments.stream()
        .map(Comment::getId)
        .collect(Collectors.toSet());
        
    return likeRepository.findLikedCommentIdsByUserAndCommentIds(
        user.getId(), 
        commentIds
    );
}
}

