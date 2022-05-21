package tr.edu.ku.devnull.kudos.entity.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ku.devnull.kudos.util.ProjectIdentifier;

import javax.persistence.*;

@Entity
@IdClass(ProjectIdentifier.class)
@Table(name = "project", schema = "public")
@Data
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    private Long projectID;

    @Id
    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "department_id", nullable = false)
    private Long departmentID;
}
