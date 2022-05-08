package tr.edu.ku.devnull.kudos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CreateUserDto {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String nickname;

    @NotNull
    private String department;

    @NotNull
    private String password;

    @NotNull
    private String authority;
}
