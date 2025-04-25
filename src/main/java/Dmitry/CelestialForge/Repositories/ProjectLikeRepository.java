package Dmitry.CelestialForge.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Entities.ProjectLike;
import Dmitry.CelestialForge.Entities.User;

public interface ProjectLikeRepository extends JpaRepository<ProjectLike, Long> {
    List<ProjectLike> findByUser(User user);
    List<ProjectLike> findByProject(Project project);
    List<ProjectLike> findByUserAndProject(User user, Project project);
    boolean existsByUserAndProject(User user, Project project);
    int countByProject(Project project);

    @Query("SELECT pl.project.id FROM ProjectLike pl WHERE pl.user = :user AND pl.project IN :projects")
    List<Long> findLikedProjectIds(@Param("user") User user, @Param("projects") List<Project> projects);
}
