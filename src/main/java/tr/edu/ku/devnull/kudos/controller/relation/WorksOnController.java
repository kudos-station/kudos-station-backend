package tr.edu.ku.devnull.kudos.controller.relation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ku.devnull.kudos.service.relation.WorksOnService;

import java.net.URI;

@RestController
public class WorksOnController {
    private final WorksOnService worksOnService;

    @Autowired
    public WorksOnController(WorksOnService worksOnService) {
        this.worksOnService = worksOnService;
    }

    @PostMapping("/admin/works-on/create-relation/{user-id}/{department-id}/{project-id}/{work-hours}")
    public ResponseEntity<?> insertToWorksOn(@PathVariable("user-id") Integer userID,
                                             @PathVariable("department-id") Integer departmentID,
                                             @PathVariable("project-id") Integer projectID,
                                             @PathVariable("work-hours") Integer workHours) {

        if (!worksOnService.insertToWorksOn(userID, departmentID, projectID, workHours))
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);

        return ResponseEntity.created(URI.create("/works-on/" + userID + "/" + departmentID + "/" + projectID + "/" + workHours)).body(HttpStatus.CREATED);
    }
}
