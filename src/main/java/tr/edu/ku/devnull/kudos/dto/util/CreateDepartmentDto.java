package tr.edu.ku.devnull.kudos.dto.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateDepartmentDto {
    private String departmentName;
    private String managerUsername;
}
