package tr.edu.ku.devnull.kudos.response.user;

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
    private List<String> department;
    private List<String> projects;
}
