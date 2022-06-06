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

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM works_in AS WI WHERE WI.user_id = (SELECT U.user_id FROM \"user\" as U WHERE U.username = ?1)", nativeQuery = true)
    int deleteWorksInRelation(String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = """
            DELETE
            FROM works_in AS WI
            WHERE WI.user_id = (SELECT U.user_id FROM "user" AS U WHERE U.username = ?1)
            AND WI.department_id = (SELECT D.department_id FROM department AS D WHERE D.department_name = ?2)""", nativeQuery = true)
    int deleteWorksInRelationWithDepartmentName(String username, String departmentName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = """
            DELETE
            FROM works_in AS WI
            WHERE WI.department_id = (SELECT D.department_id FROM department AS D WHERE D.department_name = ?1)""", nativeQuery = true)
    int deleteWorksInWithOnlyDepartmentName(String departmentName);
}
