package Dmitry.CelestialForge.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Dmitry.CelestialForge.Entities.Donation;
import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Repositories.DonationRepository;
import Dmitry.CelestialForge.Repositories.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Transactional
    public Donation donateToProject(User user, Long projectId, Double amount) {        
        // Загружаем проект из базы данных
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Проект не найден"));

        // Создаем новое пожертвование
        Donation donation = new Donation();
        donation.setUser(user);
        donation.setProject(project);
        donation.setAmount(amount);

        // Обновляем сумму собранных средств
        project.setRaisedAmount(project.getRaisedAmount() + amount);

        // Сохраняем пожертвование и обновленный проект
        donationRepository.save(donation);
        projectRepository.save(project); // Явно сохраняем изменения проекта

        return donation;
    }

    public Donation findById(Long id){
        Optional<Donation> donation = donationRepository.findById(id);
        return donation.orElse(null);
    }

    public List<Donation> findByProject(Project project) {
        return donationRepository.findByProject(project);
    }

    public List<Donation> findByUser(User user) {
        return donationRepository.findByUser(user);
    }
}
