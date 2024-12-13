package Dmitry.CelestialForge.Services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);    

    @Transactional
    public Project addProject(Project project) {
        logger.info("Сохраняем проект: {}", project.getTitle());
        logger.info("Контрибьюторы до сохранения: {}", project.getContributors());
        Project savedProject = projectRepository.save(project);
        logger.info("Контрибьюторы после сохранения: {}", savedProject.getContributors());
        return savedProject;
    }

    @Transactional(readOnly = true)
    public Project findWithAll(Long id) {
        logger.info("Загрузка проекта с ID: {}", id);
        Project project = projectRepository.findWithContributorsAndDonationsById(id)
                .orElseThrow(() -> new EntityNotFoundException("Проект с ID " + id + " не найден"));
        logger.info("Проект загружен: {}", project.getTitle());
        logger.info("Контрибьюторы проекта: {}", project.getContributors());
        logger.info("Пожертвования проекта: {}", project.getDonations());
        return project;
    }

    public boolean isOwnerOrContributor(Project project, User user) {
        logger.info("Владелец проекта: {}", project.getUser());
        if (project.getUser().equals(user)) {
            return true;
        }
        logger.info("Контрибьюторы проекта: {}", project.getContributors());
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
}
