package tr.edu.ku.devnull.kudos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateUserRoleDto {
    private String nickname;
    private String authority;
}
