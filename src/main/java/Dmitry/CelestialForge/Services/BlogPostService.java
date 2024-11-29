package Dmitry.CelestialForge.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Repositories.BlogPostRepository;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Transactional
    public BlogPost addBlogPost(BlogPost blogPost) {
        blogPost.setCreatedAt(LocalDateTime.now());
        return blogPostRepository.save(blogPost);
    }

    public BlogPost findById(Long id){
		Optional<BlogPost> blogPost = blogPostRepository.findById(id);
		if(blogPost.isPresent()) return blogPost.get();
		return null;
	}

    public List<BlogPost> findByProject(Project project) {
        return blogPostRepository.findByProject(project);
    }

    public List<BlogPost> findByUser(User user) {
        return blogPostRepository.findByUser(user);
    }
}

