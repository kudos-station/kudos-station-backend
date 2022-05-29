package tr.edu.ku.devnull.kudos.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.dto.util.CreateDepartmentDto;
import tr.edu.ku.devnull.kudos.repository.util.DepartmentRepository;
import tr.edu.ku.devnull.kudos.response.util.DepartmentNameResponse;
import tr.edu.ku.devnull.kudos.service.user.UserService;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    private final UserService userService;

    private final int IS_SUCCESSFUL = 1;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, UserService userService) {
        this.departmentRepository = departmentRepository;
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
        Integer managerID = userService.getUserIdByUsername(createDepartmentDto.getManagerName());

        return departmentRepository.insertDepartment(createDepartmentDto.getDepartmentName(), managerID) == IS_SUCCESSFUL;
    }
}

