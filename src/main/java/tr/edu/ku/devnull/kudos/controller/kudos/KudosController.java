package tr.edu.ku.devnull.kudos.controller.kudos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ku.devnull.kudos.response.kudos.KudosResponse;
import tr.edu.ku.devnull.kudos.response.kudos.KudosVariationResponse;
import tr.edu.ku.devnull.kudos.service.kudos.KudosService;

import java.util.List;

@RestController
public class KudosController {
    private final KudosService kudosService;

    @Autowired
    public KudosController(KudosService kudosService) {
        this.kudosService = kudosService;
    }

    @PostMapping(value = "/user/send-kudos/{sender-username}/{recipient-username}/{kudos-variation}")
    public ResponseEntity<?> sendKudos(@PathVariable("sender-username") String senderUsername,
                                       @PathVariable("recipient-username") String recipientUsername,
                                       @PathVariable("kudos-variation") String kudosVariation) {

        if (!kudosService.sendKudos(senderUsername, recipientUsername, kudosVariation))
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/user/recent-kudos")
    public ResponseEntity<List<KudosResponse>> recentKudos() {
        return new ResponseEntity<>(kudosService.getRecentKudos(), HttpStatus.OK);
    }

    @GetMapping(value = "/user/get-kudos/received/{username}/{limit}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<KudosResponse>> getReceivedKudosByUsernameAndLimit(@PathVariable("username") String username,
                                                                                  @PathVariable("limit") String limit) {
        return new ResponseEntity<>(kudosService.getReceivedKudosByUsernameAndLimit(username, limit), HttpStatus.OK);
    }

    @GetMapping(value = "/user/get-kudos/sent/{username}/{limit}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<KudosResponse>> getSentKudosByUsernameAndLimit(@PathVariable("username") String username,
                                                                              @PathVariable("limit") String limit) {
        return new ResponseEntity<>(kudosService.getSentKudosByUsernameAndLimit(username, limit), HttpStatus.OK);
    }

    @GetMapping("/user/kudos-variations")
    public ResponseEntity<KudosVariationResponse> getKudosVariations() {
        return new ResponseEntity<>(kudosService.getKudosVariations(), HttpStatus.OK);
    }
}
