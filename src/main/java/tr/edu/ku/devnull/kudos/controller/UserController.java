package tr.edu.ku.devnull.kudos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tr.edu.ku.devnull.kudos.dto.CreateUserDto;
import tr.edu.ku.devnull.kudos.dto.UpdateUserRoleDto;
import tr.edu.ku.devnull.kudos.mapper.UserMapper;
import tr.edu.ku.devnull.kudos.response.UserProfileResponse;
import tr.edu.ku.devnull.kudos.response.UserResponse;
import tr.edu.ku.devnull.kudos.response.UserRoleResponse;
import tr.edu.ku.devnull.kudos.service.UserService;

import java.net.URI;
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

    @PostMapping(value = "/admin/create-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody CreateUserDto user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if (!userService.createUser(user)) return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);

        return ResponseEntity.created(URI.create("/user/" + user.getNickname())).body(HttpStatus.CREATED);
    }

    @PostMapping(value = "/admin/update-user-role", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUserRole(@RequestBody UpdateUserRoleDto updateUserRoleDto) {
        if (!userService.updateUserRole(updateUserRoleDto)) return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(value = "/admin/delete-user/{nickname}")
    public ResponseEntity<?> deleteUser(@PathVariable("nickname") String nickname) {
        if (!userService.deleteUser(nickname)) return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
