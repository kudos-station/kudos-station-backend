package tr.edu.ku.devnull.kudos.entity.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authority", schema = "public")
@Data
@NoArgsConstructor
public class Authority {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "authority", nullable = false)
    private String authority;
}
