package Dmitry.CelestialForge.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Dmitry.CelestialForge.Entities.Project;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Repositories.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;  

    @Transactional
    public Project addProject(Project project) {
        Project savedProject = projectRepository.save(project);
        return savedProject;
    }

    @Transactional(readOnly = true)
    public Project findWithAll(Long id) {
        Project project = projectRepository.findWithContributorsAndDonationsById(id)
                .orElseThrow(() -> new EntityNotFoundException("Проект с ID " + id + " не найден"));
        return project;
    }

    public boolean isOwnerOrContributor(Project project, User user) {
        if (project.getUser().equals(user)) {
            return true;
        }
        return project.getContributors().contains(user);
    }
    
    public List<Project> findByUser(User user) {
        return projectRepository.findByUser(user);
    }

    public List<Project> findByTitle(String title) {
        return projectRepository.findByTitle(title);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }    

    public Optional<Project> findById(long id) {
        return projectRepository.findById(id);
    }   
}
