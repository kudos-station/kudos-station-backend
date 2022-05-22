package tr.edu.ku.devnull.kudos.response.kudos;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class KudosResponse {
    private Timestamp createdAt;

    private String recipientUsername;

    private String senderUsername;

    private String variation;
}
