package tr.edu.ku.devnull.kudos.service.relation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.repository.relation.WorksInRepository;

@Service
public class WorksInService {
    private final WorksInRepository worksInRepository;

    private final int IS_SUCCESSFUL = 1;

    @Autowired
    public WorksInService(WorksInRepository worksInRepository) {
        this.worksInRepository = worksInRepository;
    }

    public boolean insertToWorksIn(Integer userID, Integer departmentID) {
        return worksInRepository.insertToWorksIn(userID, departmentID) == IS_SUCCESSFUL;
    }
}
