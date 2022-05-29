package tr.edu.ku.devnull.kudos.dto.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateProjectDto {
    private String projectName;
    private String departmentName;
}
