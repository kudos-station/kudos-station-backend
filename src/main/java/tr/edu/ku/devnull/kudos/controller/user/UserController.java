package tr.edu.ku.devnull.kudos.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tr.edu.ku.devnull.kudos.dto.util.DepartmentDto;
import tr.edu.ku.devnull.kudos.dto.util.ProjectDto;
import tr.edu.ku.devnull.kudos.response.user.UserProfileResponse;
import tr.edu.ku.devnull.kudos.response.user.UsernameListResponse;
import tr.edu.ku.devnull.kudos.service.user.UserService;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/profile")
    public UserProfileResponse getProfile() {
        return userService.getProfile();
    }

    @GetMapping("/user/profile/{username}")
    public UserProfileResponse getUser(@PathVariable("username") String username) {
        return userService.getUser(username);
    }

    @PostMapping(value = "/user/department", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UsernameListResponse getUsersByDepartmentName(@RequestBody DepartmentDto departmentDto) {
        return userService.getUsersByDepartmentName(departmentDto.getDepartmentName());
    }

    @PostMapping(value = "/user/project", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UsernameListResponse getUsersByProjectName(@RequestBody ProjectDto projectDto) {
        return userService.getUsersByProjectName(projectDto.getProjectName());
    }
}
