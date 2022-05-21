package tr.edu.ku.devnull.kudos.entity.kudos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "kudos", schema = "public")
@Data
@NoArgsConstructor
public class Kudos {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "kudos_id")
    private Long kudosID;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "recipient_username", nullable = false)
    private String recipientUsername;

    @Column(name = "sender_username", nullable = false)
    private String senderUsername;
}
