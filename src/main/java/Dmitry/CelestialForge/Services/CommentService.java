package Dmitry.CelestialForge.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.Comment;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Repositories.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public Comment addComment(BlogPost blogPost, User user, String content) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setBlogPost(blogPost);
        comment.setUser(user);

        return commentRepository.save(comment);
    }

    public Comment findById(Long id){
		Optional<Comment> comment = commentRepository.findById(id);
		if(comment.isPresent()) return comment.get();
		return null;
	}

    public List<Comment> findByBlogPost(BlogPost blogPost) {
        return commentRepository.findByBlogPost(blogPost);
    }

    public List<Comment> findByUser(User user) {
        return commentRepository.findByUser(user);
    }
}

