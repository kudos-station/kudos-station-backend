package tr.edu.ku.devnull.kudos.response.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String firstName;
    private String lastName;
    private String department;
}
