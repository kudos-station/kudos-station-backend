package tr.edu.ku.devnull.kudos.controller.relation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ku.devnull.kudos.dto.util.WorksInDto;
import tr.edu.ku.devnull.kudos.service.relation.WorksInService;

import java.net.URI;

@RestController
public class WorksInController {
    private final WorksInService worksInService;

    @Autowired
    public WorksInController(WorksInService worksInService) {
        this.worksInService = worksInService;
    }

    @PostMapping("/admin/works-in/create-relation/")
    public ResponseEntity<?> insertToWorksIn(@RequestBody WorksInDto worksInDto) {

        if (!worksInService.insertToWorksIn(worksInDto)) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.created(URI.create("/works-in/" + worksInDto.getUsername() + "/" +
                worksInDto.getDepartmentName().replace(" ", "-").toLowerCase())).body(HttpStatus.CREATED);
    }
}
