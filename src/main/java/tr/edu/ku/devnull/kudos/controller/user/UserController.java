package tr.edu.ku.devnull.kudos.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tr.edu.ku.devnull.kudos.dto.util.DepartmentDto;
import tr.edu.ku.devnull.kudos.dto.util.ProjectDto;
import tr.edu.ku.devnull.kudos.response.user.UserIdentifierResponse;
import tr.edu.ku.devnull.kudos.response.user.UserProfileResponse;
import tr.edu.ku.devnull.kudos.response.user.UsernameAndProjectResponse;
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

    @GetMapping("/user/kudos/most-given-kudos-variation/{kudos-variation}")
    public UsernameAndProjectResponse getUserWhoGotMostOfGivenKudosVariationAndItsCurrentProject(
            @PathVariable("kudos-variation") String kudosVariation) {
        return userService.getUserWhoGotMostOfGivenKudosVariationAndItsCurrentProject(kudosVariation);
    }

    @PostMapping(value = "/user/project/obtained-all-kudos-variation/sent-any-kudos/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserIdentifierResponse getUserWhoWorksInGivenProjectAndReceivedAllKudosVariationsAndSentAnyKudos(
            @RequestBody ProjectDto projectDto) {
        return userService.getUserWhoWorksInGivenProjectAndReceivedAllKudosVariationsAndSentAnyKudos(projectDto.getProjectName());
    }

    @PostMapping(value = "/user/departments-by-name", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UsernameListResponse getUsersByDepartmentName(@RequestBody DepartmentDto departmentDto) {
        return userService.getUsersByDepartmentName(departmentDto.getDepartmentName());
    }

    @PostMapping(value = "/user/projects-by-name", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UsernameListResponse getUsersByProjectName(@RequestBody ProjectDto projectDto) {
        return userService.getUsersByProjectName(projectDto.getProjectName());
    }
}
