package tr.edu.ku.devnull.kudos.entity.kudos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "kudos_variation", schema = "public")
@Data
@NoArgsConstructor
public class KudosVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "kudos_variation_id")
    private Long kudosVariationID;

    @Column(name = "kudos_variation_name")
    private String kudosVariationName;
}

