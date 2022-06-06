package tr.edu.ku.devnull.kudos.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.dto.util.CreateProjectDto;
import tr.edu.ku.devnull.kudos.repository.relation.WorksOnRepository;
import tr.edu.ku.devnull.kudos.repository.util.ProjectRepository;
import tr.edu.ku.devnull.kudos.response.util.ProjectNameResponse;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    private final WorksOnRepository worksOnRepository;

    private final DepartmentService departmentService;

    private final int IS_SUCCESSFUL = 1;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, WorksOnRepository worksOnRepository,
                          DepartmentService departmentService) {
        this.projectRepository = projectRepository;
        this.worksOnRepository = worksOnRepository;
        this.departmentService = departmentService;
    }

    public ProjectNameResponse getAllProjectNames() {
        return ProjectNameResponse.builder()
                .projectNames(projectRepository.getAllProjectNames())
                .build();
    }

    public Integer getProjectIDByName(String projectName) {
        return projectRepository.getProjectIDByName(projectName);
    }

    public boolean insertProject(CreateProjectDto createProjectDto) {
        Integer departmentID = departmentService.getDepartmentIDByName(createProjectDto.getDepartmentName());
        return projectRepository.insertProject(createProjectDto.getProjectName(), departmentID) == IS_SUCCESSFUL;
    }

    public boolean deleteProject(String projectName) {
        worksOnRepository.deleteWorksOnRelationWithProjectName(projectName);
        int statusForProject = projectRepository.deleteProject(projectName);
        return statusForProject == IS_SUCCESSFUL;
    }
}
