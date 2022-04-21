package tr.edu.ku.devnull.kudos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloWorldController {

    @GetMapping("/")
    public String helloWorld() {
        return "Greetings from Kudos Station's Backend!";
    }
}
