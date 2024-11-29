package Dmitry.CelestialForge.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Repositories.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

	public Project addProject(Project project){
		return projectRepository.save(project);
	}

    public Project findById(Long id){
		Optional<Project> project = projectRepository.findById(id);
		if(project.isPresent()) return project.get();
		return null;
	}

    public List<Project> findByUser(User user) {
        return projectRepository.findByUser(user);
    }

    public List<Project> findByTitle(String title) {
        return projectRepository.findByTitle(title);
    }
}

