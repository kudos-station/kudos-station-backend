package tr.edu.ku.devnull.kudos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String firstName;
    private String lastName;
    private String username;
    private String department;
}
