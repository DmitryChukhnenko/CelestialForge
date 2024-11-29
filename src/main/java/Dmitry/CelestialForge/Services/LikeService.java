package Dmitry.CelestialForge.Services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.Comment;
import Dmitry.CelestialForge.Entities.Like;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Repositories.LikeRepository;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Transactional
    public Like addLikeToBlogPost(User user, BlogPost blogPost) {
        Like like = new Like();
        like.setCreatedAt(LocalDateTime.now());
        like.setUser(user);
        like.setBlogPost(blogPost);

        return likeRepository.save(like);
    }

    @Transactional
    public Like addLikeToComment(User user, Comment comment) {
        Like like = new Like();
        like.setCreatedAt(LocalDateTime.now());
        like.setUser(user);
        like.setComment(comment);

        return likeRepository.save(like);
    }

    public int countLikesForBlogPost(BlogPost blogPost) {
        return likeRepository.findByBlogPost(blogPost).size();
    }

    public int countLikesForComment(Comment comment) {
        return likeRepository.findByComment(comment).size();
    }

    public Like findById(Long id){
		Optional<Like> like = likeRepository.findById(id);
		if(like.isPresent()) return like.get();
		return null;
	}
}

