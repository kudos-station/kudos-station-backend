package tr.edu.ku.devnull.kudos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.ku.devnull.kudos.dto.KudosDto;
import tr.edu.ku.devnull.kudos.mapper.UserMapper;
import tr.edu.ku.devnull.kudos.response.KudosResponse;
import tr.edu.ku.devnull.kudos.service.KudosService;

import java.util.List;

@RestController
public class KudosController {
    private final KudosService kudosService;

    private final UserMapper userMapper;

    @Autowired
    public KudosController(KudosService kudosService, UserMapper userMapper) {
        this.kudosService = kudosService;
        this.userMapper = userMapper;
    }

    @PostMapping(value = "/user/send-kudos/{sender-nickname}/{recipient-nickname}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendKudos(@PathVariable("sender-nickname") String sender,
                                       @PathVariable("recipient-nickname") String recipient,
                                       @RequestBody KudosDto kudosDto) {

        if (!kudosService.sendKudos(sender, recipient, kudosDto))
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/user/recent-kudos")
    public ResponseEntity<List<KudosResponse>> recentKudos() {
        return new ResponseEntity<>(kudosService.getRecentKudos(), HttpStatus.OK);
    }

    @GetMapping(value = "/user/received/{nickname}/{limit}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<KudosResponse>> getReceivedKudosByNicknameAndLimit(@PathVariable("nickname") String nickname,
                                                                                  @PathVariable("limit") String limit) {
        return new ResponseEntity<>(kudosService.getReceivedKudosByNicknameAndLimit(nickname, limit), HttpStatus.OK);
    }

    @GetMapping(value = "/user/sent/{nickname}/{limit}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<KudosResponse>> getSentKudosByNicknameAndLimit(@PathVariable("nickname") String nickname,
                                                                              @PathVariable("limit") String limit) {
        return new ResponseEntity<>(kudosService.getSentKudosByNicknameAndLimit(nickname, limit), HttpStatus.OK);
    }
}
