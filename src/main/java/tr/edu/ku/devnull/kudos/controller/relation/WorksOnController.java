package tr.edu.ku.devnull.kudos.controller.relation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ku.devnull.kudos.dto.util.DeleteWorksOnDto;
import tr.edu.ku.devnull.kudos.dto.util.WorksOnDto;
import tr.edu.ku.devnull.kudos.service.relation.WorksOnService;

import java.net.URI;

@RestController
public class WorksOnController {
    private final WorksOnService worksOnService;

    @Autowired
    public WorksOnController(WorksOnService worksOnService) {
        this.worksOnService = worksOnService;
    }

    @PostMapping("/admin/works-on/create-relation")
    public ResponseEntity<?> insertToWorksOn(@RequestBody WorksOnDto worksOnDto) {

        if (!worksOnService.insertToWorksOn(worksOnDto)) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.created(URI.create("/works-on/" +
                worksOnDto.getUsername() + "/" +
                worksOnDto.getDepartmentName().replace(" ", "-").toLowerCase() + "/" +
                worksOnDto.getProjectName().replace(" ", "-").toLowerCase() + "/" +
                worksOnDto.getWorkHours())).body(HttpStatus.CREATED);
    }

    @PostMapping("/admin/works-on/delete")
    public ResponseEntity<?> removeUserFromProject(@RequestBody DeleteWorksOnDto deleteWorksOnDto) {

        if (!worksOnService.deleteWorksOnWithProjectName(deleteWorksOnDto.getUsername(), deleteWorksOnDto.getProjectName())) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
