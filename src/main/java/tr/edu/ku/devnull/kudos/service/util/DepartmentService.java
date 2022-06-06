package tr.edu.ku.devnull.kudos.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.dto.util.CreateDepartmentDto;
import tr.edu.ku.devnull.kudos.repository.relation.WorksInRepository;
import tr.edu.ku.devnull.kudos.repository.relation.WorksOnRepository;
import tr.edu.ku.devnull.kudos.repository.util.DepartmentRepository;
import tr.edu.ku.devnull.kudos.repository.util.ProjectRepository;
import tr.edu.ku.devnull.kudos.response.util.DepartmentNameResponse;
import tr.edu.ku.devnull.kudos.service.user.UserService;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    private final ProjectRepository projectRepository;

    private final WorksInRepository worksInRepository;

    private final WorksOnRepository worksOnRepository;

    private final UserService userService;

    private final int IS_SUCCESSFUL = 1;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, ProjectRepository projectRepository,
                             WorksInRepository worksInRepository, WorksOnRepository worksOnRepository,
                             UserService userService) {
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
        this.worksInRepository = worksInRepository;
        this.worksOnRepository = worksOnRepository;
        this.userService = userService;
    }

    public DepartmentNameResponse getAllDepartmentNames() {
        return DepartmentNameResponse.builder()
                .departmentNames(departmentRepository.getAllDepartmentNames())
                .build();
    }

    public Integer getDepartmentIDByName(String projectName) {
        return departmentRepository.getDepartmentIDByName(projectName);
    }

    public boolean insertDepartment(CreateDepartmentDto createDepartmentDto) {
        Integer managerID = userService.getUserIdByUsername(createDepartmentDto.getManagerUsername());

        return departmentRepository.insertDepartment(createDepartmentDto.getDepartmentName(), managerID) == IS_SUCCESSFUL;
    }

    public boolean deleteDepartment(String departmentName) {
        worksInRepository.deleteWorksInWithOnlyDepartmentName(departmentName);
        worksOnRepository.deleteWorksOnRelationWithDepartmentName(departmentName);
        projectRepository.deleteProjectsOfDepartment(departmentName);
        int statusForDepartment = departmentRepository.deleteDepartmentByName(departmentName);
        return statusForDepartment == IS_SUCCESSFUL;
    }
}

