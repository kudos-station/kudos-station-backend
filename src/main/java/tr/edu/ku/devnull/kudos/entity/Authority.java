package tr.edu.ku.devnull.kudos.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authorities", schema = "public")
@Data
@NoArgsConstructor
public class Authority {

    @Id
    @Column(name = "nickname")
    private Long nickname;

    @Column(name = "authority", nullable = false)
    private Long authority;
}
