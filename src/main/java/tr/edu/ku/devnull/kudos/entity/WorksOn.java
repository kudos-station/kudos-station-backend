package tr.edu.ku.devnull.kudos.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ku.devnull.kudos.util.WorksOnIdentifier;

import javax.persistence.*;

@Entity
@IdClass(WorksOnIdentifier.class)
@Table(name = "works_on", schema = "public")
@Data
@NoArgsConstructor
public class WorksOn {
    @Id
    @Column(name = "user_id")
    private Long userID;

    @Column(name = "department_id")
    private Long departmentID;

    @Id
    @Column(name = "project_id", nullable = false)
    private String projectID;

    @Column(name = "work_hours", nullable = false)
    private Long workHours;
}