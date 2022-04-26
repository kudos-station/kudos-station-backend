package tr.edu.ku.devnull.kudos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ku.devnull.kudos.mapper.UserMapper;
import tr.edu.ku.devnull.kudos.response.UserResponse;
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

    @GetMapping("/admin/get-sample-user")
    public UserResponse getUser() {
        return UserResponse.builder()
                .firstName("Kaan")
                .lastName("Turkmen")
                .department("Computer Engineering")
                .build();
    }

    @GetMapping("/user/get-profile")
    public UserResponse getProfile() {
        return UserResponse.builder()
                .firstName("Can")
                .lastName("Usluel")
                .department("Computer Engineering")
                .build();
    }

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello world!";
    }

    @GetMapping("/admin/get-all-users")
    public List<UserResponse> getAllUsers() {
        return userMapper.dtoToResponseList(userService.getAllUsers());
    }
}
