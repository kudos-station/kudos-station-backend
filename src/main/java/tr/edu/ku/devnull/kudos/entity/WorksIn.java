package tr.edu.ku.devnull.kudos.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ku.devnull.kudos.util.WorksInIdentifier;

import javax.persistence.*;

@Entity
@IdClass(WorksInIdentifier.class)
@Table(name = "works_on", schema = "public")
@Data
@NoArgsConstructor
public class WorksIn {
    @Id
    @Column(name = "user_id")
    private Long userID;

    @Id
    @Column(name = "department_id")
    private Long departmentID;
}
