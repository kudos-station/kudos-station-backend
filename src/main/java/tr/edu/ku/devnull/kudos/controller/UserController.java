package tr.edu.ku.devnull.kudos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ku.devnull.kudos.response.UserProfileResponse;
import tr.edu.ku.devnull.kudos.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/profile/")
    public UserProfileResponse getProfile() {
        return userService.getProfile();
    }

    @GetMapping("/user/profile/{username}")
    public UserProfileResponse getUser(@PathVariable("username") String username) {
        return userService.getUser(username);
    }
}
