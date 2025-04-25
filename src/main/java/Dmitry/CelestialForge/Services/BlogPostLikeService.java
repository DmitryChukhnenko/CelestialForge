package Dmitry.CelestialForge.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.BlogPostLike;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Repositories.BlogPostLikeRepository;

@Service
public class BlogPostLikeService {
    private static final String LIKES_COUNT_CACHE = "blogPost_likes_count";

    @Autowired
    private BlogPostLikeRepository likeRepository;

    @Transactional
    @CacheEvict(value = LIKES_COUNT_CACHE, key = "#blogPost.id")
    public BlogPostLike toggleLike(User user, BlogPost blogPost) {
        BlogPostLike existing = findConcrete(user, blogPost);
        if(existing != null) {
            likeRepository.delete(existing);
            return null;
        }
        
        BlogPostLike like = new BlogPostLike();
        like.setUser(user);
        like.setBlogPost(blogPost);
        return likeRepository.save(like);
    }

    @Cacheable(value = LIKES_COUNT_CACHE, key = "#blogPost.id")
    public int countLikes(BlogPost blogPost) {
        return likeRepository.countByBlogPost(blogPost);
    }

    public boolean hasUserLiked(User user, BlogPost blogPost) {
        return likeRepository.existsByUserAndBlogPost(user, blogPost);
    }

    public BlogPostLike findConcrete(User user, BlogPost blogPost){
		List<BlogPostLike> likes = likeRepository.findByUserAndBlogPost(user, blogPost);
		if(likes.size() > 0) return likes.get(0);
		return null;
	}

    public BlogPostLike findById(Long id){
		Optional<BlogPostLike> like = likeRepository.findById(id);
		if(like.isPresent()) return like.get();
		return null;
	}
}

