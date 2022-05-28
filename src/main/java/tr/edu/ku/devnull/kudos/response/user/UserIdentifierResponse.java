package tr.edu.ku.devnull.kudos.response.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserIdentifierResponse {
    private String username;
    private String firstName;
    private String lastName;
}
