package tr.edu.ku.devnull.kudos.response.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UsernameListResponse {
    private List<String> usernames;
}
