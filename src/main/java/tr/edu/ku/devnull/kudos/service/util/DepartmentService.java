package tr.edu.ku.devnull.kudos.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.repository.util.DepartmentRepository;
import tr.edu.ku.devnull.kudos.response.util.DepartmentIDResponse;
import tr.edu.ku.devnull.kudos.response.util.DepartmentNameResponse;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    private final int IS_SUCCESSFUL = 1;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentNameResponse getAllDepartmentNames() {
        return DepartmentNameResponse.builder()
                .departmentNames(departmentRepository.getAllDepartmentNames())
                .build();
    }

    public DepartmentIDResponse getDepartmentIDByName(String projectName) {
        return DepartmentIDResponse.builder()
                .departmentID(departmentRepository.getDepartmentIDByName(projectName))
                .build();
    }

    public boolean insertDepartment(String departmentName, Integer managerID) {
        return departmentRepository.insertDepartment(departmentName, managerID) == IS_SUCCESSFUL;
    }
}

