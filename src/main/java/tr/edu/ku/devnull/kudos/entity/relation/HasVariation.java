package tr.edu.ku.devnull.kudos.entity.relation;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "has_variation", schema = "public")
@Data
@NoArgsConstructor
public class HasVariation {
    @Id
    @Column(name = "kudos_id")
    private Long kudosID;

    @Column(name = "kudos_variation_id")
    private Long kudosVariationID;
}
