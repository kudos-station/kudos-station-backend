package tr.edu.ku.devnull.kudos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
    private String firstName;
    private String lastName;
    private String department;
    private String nickname;
}
