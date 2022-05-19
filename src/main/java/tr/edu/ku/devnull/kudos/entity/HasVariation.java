package tr.edu.ku.devnull.kudos.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

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