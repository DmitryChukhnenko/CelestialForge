package Dmitry.CelestialForge.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Entities.ProjectLike;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Repositories.ProjectLikeRepository;

@Service
public class ProjectLikeService {
    private static final String LIKES_COUNT_CACHE = "project_likes_count";

    @Autowired
    private ProjectLikeRepository likeRepository;

    @Transactional
    @CacheEvict(value = LIKES_COUNT_CACHE, key = "#project.id")
    public ProjectLike toggleLike(User user, Project project) {
        ProjectLike existing = findConcrete(user, project);
        if(existing != null) {
            likeRepository.delete(existing);
            return null;
        }
        
        ProjectLike like = new ProjectLike();
        like.setUser(user);
        like.setProject(project);
        return likeRepository.save(like);
    }

    @Cacheable(value = LIKES_COUNT_CACHE, key = "#project.id")
    public int countLikes(Project project) {
        return likeRepository.countByProject(project);
    }

    public boolean hasUserLiked(User user, Project project) {
        return likeRepository.existsByUserAndProject(user, project);
    }

    public ProjectLike findConcrete(User user, Project project){
		List<ProjectLike> likes = likeRepository.findByUserAndProject(user, project);
		if(likes.size() > 0) return likes.get(0);
		return null;
	}

    public ProjectLike findById(Long id){
		Optional<ProjectLike> like = likeRepository.findById(id);
		if(like.isPresent()) return like.get();
		return null;
	}
}

