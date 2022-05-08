package tr.edu.ku.devnull.kudos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class KudosDto {
    @Size(min = 10, max = 300)
    @NotNull
    String content;
}
