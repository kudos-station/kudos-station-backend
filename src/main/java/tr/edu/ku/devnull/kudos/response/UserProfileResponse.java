package tr.edu.ku.devnull.kudos.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserProfileResponse {
    private String firstName;
    private String lastName;
    private String username;
    private String authorities;
    private String department;
    private List<String> projects;
}
