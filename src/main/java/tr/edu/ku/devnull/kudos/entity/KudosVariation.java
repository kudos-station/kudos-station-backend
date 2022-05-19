package tr.edu.ku.devnull.kudos.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "kudos_variation", schema = "public")
@Data
@NoArgsConstructor
public class KudosVariation {
    @Id
    @Column(name = "kudos_variation_id")
    private Long kudosVariationID;

    @Column(name = "kudos_variation_name")
    private Long kudosVariationName;
}

