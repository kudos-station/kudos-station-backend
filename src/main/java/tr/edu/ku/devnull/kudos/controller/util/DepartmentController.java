package tr.edu.ku.devnull.kudos.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ku.devnull.kudos.response.util.DepartmentIDResponse;
import tr.edu.ku.devnull.kudos.response.util.DepartmentNameResponse;
import tr.edu.ku.devnull.kudos.service.util.DepartmentService;

import java.net.URI;

@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @GetMapping("/admin/department/all")
    public ResponseEntity<DepartmentNameResponse> getAllProjectNames() {
        return new ResponseEntity<>(departmentService.getAllDepartmentNames(), HttpStatus.OK);
    }

    @GetMapping("/admin/department/department-id/{department-name}")
    public ResponseEntity<DepartmentIDResponse> getProjectIDByName(@PathVariable("department-name") String departmentName) {
        return new ResponseEntity<>(departmentService.getDepartmentIDByName(departmentName), HttpStatus.OK);
    }

    @PostMapping("/admin/project/create-department/{department-name}/{manager-id}")
    public ResponseEntity<?> createProject(@PathVariable("department-name") String departmentName,
                                           @PathVariable("manager-id") Integer managerID) {

        if (!departmentService.insertDepartment(departmentName, managerID))
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);

        return ResponseEntity.created(URI.create("/department/" + departmentName)).body(HttpStatus.CREATED);
    }
}
