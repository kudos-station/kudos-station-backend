package tr.edu.ku.devnull.kudos.entity.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "department", schema = "public")
@Data
@NoArgsConstructor
public class Department {
    @Id
    @Column(name = "department_id")
    private Long departmentID;

    @Column(name = "department_name", nullable = false)
    private String departmentName;

    @Column(name = "manager_id", nullable = false)
    private Long managerID;
}
