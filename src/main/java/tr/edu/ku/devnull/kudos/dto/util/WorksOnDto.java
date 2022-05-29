package tr.edu.ku.devnull.kudos.dto.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorksOnDto {
    private String username;
    private String departmentName;
    private String projectName;
    private Integer workHours;
}
