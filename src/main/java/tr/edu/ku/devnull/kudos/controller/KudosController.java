package tr.edu.ku.devnull.kudos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ku.devnull.kudos.dto.KudosDto;
import tr.edu.ku.devnull.kudos.mapper.UserMapper;
import tr.edu.ku.devnull.kudos.service.KudosService;

@RestController
public class KudosController {
    private final KudosService kudosService;

    private final UserMapper userMapper;

    @Autowired
    public KudosController(KudosService kudosService, UserMapper userMapper) {
        this.kudosService = kudosService;
        this.userMapper = userMapper;
    }

    @PostMapping(value = "/user/{sender-nickname}/{recipient-nickname}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendKudos(@PathVariable("sender-nickname") String sender,
                                       @PathVariable("recipient-nickname") String recipient,
                                       @RequestBody KudosDto kudosDto) {

        if (!kudosService.sendKudos(sender, recipient, kudosDto))
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
