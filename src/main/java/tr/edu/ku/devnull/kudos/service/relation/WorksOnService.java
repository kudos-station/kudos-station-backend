package tr.edu.ku.devnull.kudos.service.relation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.dto.util.WorksOnDto;
import tr.edu.ku.devnull.kudos.repository.relation.WorksOnRepository;
import tr.edu.ku.devnull.kudos.service.user.UserService;
import tr.edu.ku.devnull.kudos.service.util.DepartmentService;
import tr.edu.ku.devnull.kudos.service.util.ProjectService;

@Service
public class WorksOnService {
    private final WorksOnRepository worksOnRepository;

    private final ProjectService projectService;

    private final DepartmentService departmentService;

    private final UserService userService;

    private final int IS_SUCCESSFUL = 1;

    @Autowired
    public WorksOnService(WorksOnRepository worksOnRepository, ProjectService projectService,
                          DepartmentService departmentService, UserService userService) {
        this.worksOnRepository = worksOnRepository;
        this.projectService = projectService;
        this.departmentService = departmentService;
        this.userService = userService;
    }

    public boolean insertToWorksOn(WorksOnDto worksOnDto) {
        Integer userID = userService.getUserIdByUsername(worksOnDto.getUsername());
        Integer departmentID = departmentService.getDepartmentIDByName(worksOnDto.getDepartmentName());
        Integer projectID = projectService.getProjectIDByName(worksOnDto.getProjectName());

        return worksOnRepository.insertToWorksOn(userID, departmentID, projectID, worksOnDto.getWorkHours()) == IS_SUCCESSFUL;
    }

    public boolean insertToWorksOnTest(Integer userID, Integer departmentID, Integer projectID, Integer workHours) {
        return worksOnRepository.insertToWorksOn(userID, departmentID, projectID, workHours) == IS_SUCCESSFUL;
    }
}
