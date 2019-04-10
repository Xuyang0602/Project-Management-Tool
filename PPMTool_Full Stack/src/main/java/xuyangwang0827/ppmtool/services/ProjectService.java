package xuyangwang0827.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xuyangwang0827.ppmtool.domain.Project;
import xuyangwang0827.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        // Logic
        return projectRepository.save(project);
    }
}
