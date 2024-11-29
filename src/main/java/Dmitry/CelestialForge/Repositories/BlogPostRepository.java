package Dmitry.CelestialForge.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Entities.User;

import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    List<BlogPost> findByUser(User user);
    List<BlogPost> findByProject(Project project);
}

