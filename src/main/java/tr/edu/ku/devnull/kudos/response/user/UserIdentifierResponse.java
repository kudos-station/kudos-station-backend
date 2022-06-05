package tr.edu.ku.devnull.kudos.response.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserIdentifierResponse {
    private List<String> usernames;
    private List<String> firstNames;
    private List<String> lastNames;
}
