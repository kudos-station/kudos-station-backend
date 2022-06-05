package tr.edu.ku.devnull.kudos.service.relation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.dto.util.WorksInDto;
import tr.edu.ku.devnull.kudos.repository.relation.WorksInRepository;
import tr.edu.ku.devnull.kudos.service.user.UserService;
import tr.edu.ku.devnull.kudos.service.util.DepartmentService;

@Service
public class WorksInService {
    private final WorksInRepository worksInRepository;

    private final UserService userService;

    private final DepartmentService departmentService;

    private final int IS_SUCCESSFUL = 1;

    @Autowired
    public WorksInService(WorksInRepository worksInRepository, UserService userService, DepartmentService departmentService) {
        this.worksInRepository = worksInRepository;
        this.userService = userService;
        this.departmentService = departmentService;
    }

    public boolean insertToWorksIn(WorksInDto worksInDto) {
        Integer userID = userService.getUserIdByUsername(worksInDto.getUsername());
        Integer departmentID = departmentService.getDepartmentIDByName(worksInDto.getDepartmentName());

        return worksInRepository.insertToWorksIn(userID, departmentID) == IS_SUCCESSFUL;
    }

    public boolean insertToWorksInTest(Integer userID, Integer departmentID) {
        return worksInRepository.insertToWorksIn(userID, departmentID) == IS_SUCCESSFUL;
    }
}
