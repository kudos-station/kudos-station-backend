package tr.edu.ku.devnull.kudos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ku.devnull.kudos.mapper.UserMapper;
import tr.edu.ku.devnull.kudos.response.UserProfileResponse;
import tr.edu.ku.devnull.kudos.response.UserResponse;
import tr.edu.ku.devnull.kudos.response.UserRoleResponse;
import tr.edu.ku.devnull.kudos.service.UserService;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello world!";
    }

    @GetMapping("/user/profile/")
    public UserProfileResponse getProfile() {
        return userService.getProfile();
    }

    @GetMapping("/user/profile/{nickname}")
    public UserProfileResponse getUser(@PathVariable("nickname") String nickname) {
        return userService.getUser(nickname);
    }

    @GetMapping("/admin/get-current-user")
    public UserRoleResponse getUserWithAuth() {
        return userService.getCurrentUserWithAuthorities();
    }

    @GetMapping("/admin/get-all-users")
    public List<UserResponse> getAllUsers() {
        return userMapper.dtoToResponseList(userService.getAllUsers());
    }
}
