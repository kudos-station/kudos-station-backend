package tr.edu.ku.devnull.kudos.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ku.devnull.kudos.response.util.ProjectIDResponse;
import tr.edu.ku.devnull.kudos.response.util.ProjectNameResponse;
import tr.edu.ku.devnull.kudos.service.util.ProjectService;

import java.net.URI;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping("/admin/project/all")
    public ResponseEntity<ProjectNameResponse> getAllProjectNames() {
        return new ResponseEntity<>(projectService.getAllProjectNames(), HttpStatus.OK);
    }

    @GetMapping("/admin/project/project-id/{project-name}")
    public ResponseEntity<ProjectIDResponse> getProjectIDByName(@PathVariable("project-name") String projectName) {
        return new ResponseEntity<>(projectService.getProjectIDByName(projectName), HttpStatus.OK);
    }

    @PostMapping("/admin/project/create-project/{project-name}/{department-id}")
    public ResponseEntity<?> createProject(@PathVariable("project-name") String projectName,
                                           @PathVariable("department-id") Integer departmentID) {

        if (!projectService.insertProject(projectName, departmentID))
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);

        return ResponseEntity.created(URI.create("/department/" + departmentID)).body(HttpStatus.CREATED);
    }
}
