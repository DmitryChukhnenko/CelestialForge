package Dmitry.CelestialForge.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Entities.User;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    @EntityGraph(attributePaths = {"comments", "user"})
    List<BlogPost> findByUser(User user);
    @EntityGraph(attributePaths = {"comments", "user"})
    List<BlogPost> findByProject(Project project);
    @Override
    @EntityGraph(attributePaths = {"comments", "user"})
    Optional<BlogPost> findById(Long id);
}

