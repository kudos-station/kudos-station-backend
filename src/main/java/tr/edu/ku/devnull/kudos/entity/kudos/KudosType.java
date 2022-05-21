package tr.edu.ku.devnull.kudos.entity.kudos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "kudos_type", schema = "public")
@Data
@NoArgsConstructor
public class KudosType {
    @Id
    @Column(name = "kudos_id")
    private Long kudosID;

    @Column(name = "kudos_type")
    private Long kudosType;
}

