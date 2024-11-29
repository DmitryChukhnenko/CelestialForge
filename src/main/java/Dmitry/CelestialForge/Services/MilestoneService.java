package Dmitry.CelestialForge.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Dmitry.CelestialForge.Entities.Milestone;
import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Repositories.MilestoneRepository;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Transactional
    public Milestone addBlogPost(Milestone milestone) {
        milestone.setCreatedAt(LocalDateTime.now());
        return milestoneRepository.save(milestone);
    }

    public Milestone findById(Long id){
		Optional<Milestone> milestone = milestoneRepository.findById(id);
		if(milestone.isPresent()) return milestone.get();
		return null;
	}

    public List<Milestone> findByProject(Project project) {
        return milestoneRepository.findByProject(project);
    }
}

