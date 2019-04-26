package xuyangwang0827.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xuyangwang0827.ppmtool.domain.Backlog;
import xuyangwang0827.ppmtool.domain.Project;
import xuyangwang0827.ppmtool.domain.ProjectTask;
import xuyangwang0827.ppmtool.exceptions.ProjectNotFoundException;
import xuyangwang0827.ppmtool.repositories.BacklogRepository;
import xuyangwang0827.ppmtool.repositories.ProjectRepository;
import xuyangwang0827.ppmtool.repositories.ProjectTaskRepository;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        try {
            // ProjectTasks to be added to a specific project, project != null, backlog exists
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());

            // Set the backlog to projectTask
            projectTask.setBacklog(backlog);

            // We want our project sequence to be like this: IDPRO-1 IDPRO-2 ...100 101
            Integer backlogSequence = backlog.getPTSequence();

            // Update the backlog Sequence
            backlogSequence++;
            backlog.setPTSequence(backlogSequence);

            // Add Sequence to Project Task
            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            // Initial priority when priority is null
            if (projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }

            // Initial status when status is null
            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project bot Found");
        }


    }

    public Iterable<ProjectTask> findBacklogById(String id) {
        Project project = projectRepository.findByProjectIdentifier(id.toUpperCase());
        if (project == null) throw new ProjectNotFoundException("Project with id: '" + id + "' does not exist");

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findProjectTaskByProjectSequence(String backlog_id, String sequence_id) {
        // Make sure we are searching on an existing backlog
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if (backlog == null) throw new ProjectNotFoundException("Project with backlog Id: '" + backlog_id + "' does not exist");

        // Make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(sequence_id);
        if (projectTask == null) throw new ProjectNotFoundException("Project with Sequence Num: '" + sequence_id + "' does not exist");

        // Make sure that backlog/project id in the path corresponds to the right project
        if (!projectTask.getProjectIdentifier().equals(backlog_id)) throw new ProjectNotFoundException("Project Task '" + sequence_id + "' does not exist in project '" + backlog_id + "'");

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
        ProjectTask projectTask = findProjectTaskByProjectSequence(backlog_id, pt_id);
//        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deleteProjectTaskByProjectSequence(String backlog_id, String pt_id) {
        ProjectTask projectTask = findProjectTaskByProjectSequence(backlog_id, pt_id);

        projectTaskRepository.delete(projectTask);
    }
}
