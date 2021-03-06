package tr.edu.ku.devnull.kudos.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ku.devnull.kudos.dto.user.CreateUserDto;
import tr.edu.ku.devnull.kudos.dto.user.UpdateUserRoleDto;
import tr.edu.ku.devnull.kudos.service.user.UserEditService;

import java.net.URI;

@RestController
public class UserEditController {

    private final UserEditService userEditService;

    @Autowired
    public UserEditController(UserEditService userEditService) {
        this.userEditService = userEditService;
    }

    @PostMapping(value = "/admin/create-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody CreateUserDto user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if (!userEditService.createUser(user)) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.created(URI.create("/user/" + user.getUsername())).body(HttpStatus.CREATED);
    }

    @PostMapping(value = "/admin/update-user-role", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUserRole(@RequestBody UpdateUserRoleDto updateUserRoleDto) {

        if (!userEditService.updateUserRole(updateUserRoleDto)) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value = "/admin/delete-user/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        if (!userEditService.deleteUser(username)) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
