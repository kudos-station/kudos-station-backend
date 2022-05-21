package tr.edu.ku.devnull.kudos.repository.relation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.ku.devnull.kudos.entity.relation.WorksIn;

public interface WorksInRepository extends JpaRepository<WorksIn, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO works_in(user_id, department_id) VALUES (?1, ?2)", nativeQuery = true)
    int insertToWorksIn(Integer userID, Integer departmentID);
}
