package tr.edu.ku.devnull.kudos.service.relation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.repository.relation.WorksOnRepository;

@Service
public class WorksOnService {
    private final WorksOnRepository worksOnRepository;

    private final int IS_SUCCESSFUL = 1;

    @Autowired
    public WorksOnService(WorksOnRepository worksOnRepository) {
        this.worksOnRepository = worksOnRepository;
    }

    public boolean insertToWorksOn(Integer userID, Integer departmentID, Integer projectID, Integer workHours) {
        return worksOnRepository.insertToWorksOn(userID, departmentID, projectID, workHours) == IS_SUCCESSFUL;
    }
}
