package tr.edu.ku.devnull.kudos.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ku.devnull.kudos.dto.util.CreateProjectDto;
import tr.edu.ku.devnull.kudos.dto.util.ProjectDto;
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

    @PostMapping("/admin/project/create-project")
    public ResponseEntity<?> createProject(@RequestBody CreateProjectDto createProjectDto) {

        if (!projectService.insertProject(createProjectDto)) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.created(URI.create("/department/" +
                        createProjectDto.getProjectName().replace(" ", "-").toLowerCase()))
                .body(HttpStatus.CREATED);
    }

    @PostMapping("/admin/project/delete-project")
    public ResponseEntity<?> deleteProject(@RequestBody ProjectDto projectDto) {

        if (!projectService.deleteProject(projectDto.getProjectName())) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
