package tr.edu.ku.devnull.kudos.repository.relation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.ku.devnull.kudos.entity.relation.WorksOn;

public interface WorksOnRepository extends JpaRepository<WorksOn, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO works_on(user_id, department_id, project_id, work_hours) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
    int insertToWorksOn(Integer userID, Integer departmentID, Integer projectID, Integer workHours);
}
