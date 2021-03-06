package tr.edu.ku.devnull.kudos.controller.kudos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.ku.devnull.kudos.dto.util.DepartmentDto;
import tr.edu.ku.devnull.kudos.response.kudos.KudosCountResponse;
import tr.edu.ku.devnull.kudos.response.kudos.KudosResponse;
import tr.edu.ku.devnull.kudos.response.kudos.KudosVariationResponse;
import tr.edu.ku.devnull.kudos.response.kudos.ScoreboardResponse;
import tr.edu.ku.devnull.kudos.response.user.UsernameListResponse;
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

        if (!kudosService.sendKudos(senderUsername, recipientUsername, kudosVariation)) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/user/recent-kudos")
    public ResponseEntity<List<KudosResponse>> recentKudos() {
        return new ResponseEntity<>(kudosService.getRecentKudos(), HttpStatus.OK);
    }

    @GetMapping(value = "/user/kudos/received/{username}/{limit}")
    public ResponseEntity<List<KudosResponse>> getReceivedKudosByUsernameAndLimit(@PathVariable("username") String username,
                                                                                  @PathVariable("limit") String limit) {
        return new ResponseEntity<>(kudosService.getReceivedKudosByUsernameAndLimit(username, limit), HttpStatus.OK);
    }

    @GetMapping(value = "/user/kudos/sent/{username}/{limit}")
    public ResponseEntity<List<KudosResponse>> getSentKudosByUsernameAndLimit(@PathVariable("username") String username,
                                                                              @PathVariable("limit") String limit) {
        return new ResponseEntity<>(kudosService.getSentKudosByUsernameAndLimit(username, limit), HttpStatus.OK);
    }

    @GetMapping("/user/kudos-variations")
    public ResponseEntity<KudosVariationResponse> getKudosVariations() {
        return new ResponseEntity<>(kudosService.getKudosVariations(), HttpStatus.OK);
    }

    @GetMapping("/user/kudos/kudos-variation/{kudos-variation-name}/given-range")
    public ResponseEntity<UsernameListResponse> getUsersWhoSentAmountOfKudosButDidNotHasAmountOfKudos(@PathVariable("kudos-variation-name") String kudosVariationName) {
        return new ResponseEntity<>(kudosService.getUsersWhoSentAmountOfKudosButDidNotHasAmountOfKudos(kudosVariationName), HttpStatus.OK);
    }

    @GetMapping("/user/scoreboard")
    public ResponseEntity<List<ScoreboardResponse>> getScoreboard() {
        return new ResponseEntity<>(kudosService.getScoreboard(), HttpStatus.OK);
    }

    @GetMapping("/user/total-kudos/{username}")
    public ResponseEntity<KudosCountResponse> getScoreboard(@PathVariable("username") String username) {
        return new ResponseEntity<>(kudosService.getKudosCount(username), HttpStatus.OK);
    }

    @PostMapping(value = "/user/kudos/received-all-variations/from-department", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsernameListResponse> getUsersWhoWorksInGivenDepartmentAndGotAllKudosVariations(
            @RequestBody DepartmentDto departmentDto) {

        return new ResponseEntity<>(kudosService.getUsersWhoWorksInGivenDepartmentAndGotAllKudosVariations(departmentDto.getDepartmentName()), HttpStatus.OK);
    }

    @PostMapping(value = "/user/kudos/works-in-all-projects/from-department", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<KudosResponse>> getKudosFromUsersThatWorkInAllProjectsInGivenDepartment(
            @RequestBody DepartmentDto departmentDto) {

        return new ResponseEntity<>(kudosService.getKudosFromUsersThatWorkInAllProjectsInGivenDepartment(departmentDto.getDepartmentName()), HttpStatus.OK);
    }
}
