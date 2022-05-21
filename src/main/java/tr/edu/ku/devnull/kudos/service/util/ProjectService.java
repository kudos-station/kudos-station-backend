package tr.edu.ku.devnull.kudos.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.repository.util.ProjectRepository;
import tr.edu.ku.devnull.kudos.response.util.ProjectIDResponse;
import tr.edu.ku.devnull.kudos.response.util.ProjectNameResponse;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    private final int IS_SUCCESSFUL = 1;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectNameResponse getAllProjectNames() {
        return ProjectNameResponse.builder()
                .projectNames(projectRepository.getAllProjectNames())
                .build();
    }

    public ProjectIDResponse getProjectIDByName(String projectName) {
        return ProjectIDResponse.builder()
                .projectID(projectRepository.getProjectIDByName(projectName))
                .build();
    }

    public boolean insertProject(String projectName, Integer departmentID) {
        return projectRepository.insertProject(projectName, departmentID) == IS_SUCCESSFUL;
    }
}
