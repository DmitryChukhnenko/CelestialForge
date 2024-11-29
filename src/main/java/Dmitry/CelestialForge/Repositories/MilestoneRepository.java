package Dmitry.CelestialForge.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Dmitry.CelestialForge.Entities.Milestone;
import Dmitry.CelestialForge.Entities.Project;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    List<Milestone> findByProject(Project project);
}

