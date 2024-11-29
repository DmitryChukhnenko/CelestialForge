package Dmitry.CelestialForge.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Dmitry.CelestialForge.Entities.Donation;
import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Repositories.DonationRepository;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    // Где-то тут должна быть API для переводов, но её нет
    @Transactional
    public Donation donateToProject(User user, Project project, Double amount) {        
        Donation donation = new Donation();
        donation.setUser(user);
        donation.setProject(project);
        donation.setAmount(amount);

        project.setRaisedAmount(project.getRaisedAmount() + amount);
        return donationRepository.save(donation);
    }

    public Donation findById(Long id){
		Optional<Donation> donation = donationRepository.findById(id);
		if(donation.isPresent()) return donation.get();
		return null;
	}
}

