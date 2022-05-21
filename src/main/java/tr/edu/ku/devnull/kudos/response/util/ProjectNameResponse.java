package tr.edu.ku.devnull.kudos.response.util;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectNameResponse {
    private List<String> projectNames;
}
