package tr.edu.ku.devnull.kudos.entity;

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
    private Long kudosId;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "recipient_nickname", nullable = false)
    private String recipient;

    @Column(name = "sender_nickname", nullable = false)
    private String sender;

    @Column(name = "content", nullable = false)
    private String content;
}
