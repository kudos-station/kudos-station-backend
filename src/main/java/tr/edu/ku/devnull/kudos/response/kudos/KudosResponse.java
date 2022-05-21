package tr.edu.ku.devnull.kudos.response.kudos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class KudosResponse {
    private Timestamp createdAt;

    private String recipient;

    private String sender;

    private String content;
}
