package Dmitry.CelestialForge.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Entities.User;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUser(User user);
    List<Project> findByTitle(String title);
    @EntityGraph(attributePaths = {"user", "contributors", "donations.user", "blogPosts", "milestones"})
    Optional<Project> findWithContributorsAndDonationsById(Long id);
}

