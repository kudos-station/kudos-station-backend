package tr.edu.ku.devnull.kudos.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ku.devnull.kudos.dto.util.CreateDepartmentDto;
import tr.edu.ku.devnull.kudos.dto.util.DepartmentDto;
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

    @PostMapping("/admin/department/create-department")
    public ResponseEntity<?> createDepartment(@RequestBody CreateDepartmentDto createDepartmentDto) {

        if (!departmentService.insertDepartment(createDepartmentDto)) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.created(URI.create("/department/" +
                createDepartmentDto.getDepartmentName().replace(' ', '-').toLowerCase())).body(HttpStatus.CREATED);
    }

    @PostMapping("/admin/department/delete-department")
    public ResponseEntity<?> deleteDepartment(@RequestBody DepartmentDto departmentDto) {
        if (!departmentService.deleteDepartment(departmentDto.getDepartmentName())) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
