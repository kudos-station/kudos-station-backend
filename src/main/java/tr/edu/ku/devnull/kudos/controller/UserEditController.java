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
import tr.edu.ku.devnull.kudos.service.UserEditService;

import java.net.URI;

@RestController
public class UserEditController {

    private final UserEditService userEditService;

    private final UserMapper userMapper;

    @Autowired
    public UserEditController(UserEditService userEditService, UserMapper userMapper) {
        this.userEditService = userEditService;
        this.userMapper = userMapper;
    }

    @PostMapping(value = "/admin/create-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody CreateUserDto user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if (!userEditService.createUser(user)) return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);

        return ResponseEntity.created(URI.create("/user/" + user.getNickname())).body(HttpStatus.CREATED);
    }

    @PostMapping(value = "/admin/update-user-role", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUserRole(@RequestBody UpdateUserRoleDto updateUserRoleDto) {
        if (!userEditService.updateUserRole(updateUserRoleDto))
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(value = "/admin/delete-user/{nickname}")
    public ResponseEntity<?> deleteUser(@PathVariable("nickname") String nickname) {
        if (!userEditService.deleteUser(nickname)) return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
