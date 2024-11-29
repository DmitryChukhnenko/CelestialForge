package Dmitry.CelestialForge.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Dmitry.CelestialForge.Entities.Donation;
import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Entities.User;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByProject(Project project);
    List<Donation> findByUser(User user);
}

