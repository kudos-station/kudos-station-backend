package tr.edu.ku.devnull.kudos.dto.kudos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class KudosIdentifierDto {

    private Integer kudosID;

    private Timestamp createdAt;

    private String recipientUsername;

    private String senderUsername;

    private String variation;
}
