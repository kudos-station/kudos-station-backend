package tr.edu.ku.devnull.kudos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KudosApplication {

    public static void main(String[] args) {
        SpringApplication.run(KudosApplication.class, args);
    }

}
