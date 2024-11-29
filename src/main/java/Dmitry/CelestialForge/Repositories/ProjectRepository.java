package Dmitry.CelestialForge.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Entities.User;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUser(User user);
    List<Project> findByTitle(String title);
}

