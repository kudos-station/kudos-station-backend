package tr.edu.ku.devnull.kudos.response.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsernameAndProjectResponse {
    private String username;
    private String project;
}
