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

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = """
            DELETE
            FROM works_on AS WO
            WHERE WO.user_id = (SELECT U.user_id FROM "user" AS U WHERE U.username = ?1)""", nativeQuery = true)
    int deleteWorksOnRelation(String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = """
            DELETE
            FROM works_on AS WO
            WHERE WO.user_id = (SELECT U.user_id FROM "user" AS U WHERE U.username = ?1)
              AND WO.project_id = (SELECT P.project_id FROM project AS P WHERE P.project_name = ?2)""", nativeQuery = true)
    int deleteWorksOnRelationWithProjectName(String username, String projectName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = """
            DELETE
            FROM works_on AS WO
            WHERE WO.project_id IN (SELECT P.project_id
                                    FROM project AS P,
                                         works_on AS WO,
                                         department AS D
                                    WHERE P.project_id = WO.project_id
                                      AND WO.department_id = D.department_id
                                      AND D.department_name = ?1)""", nativeQuery = true)
    int deleteWorksOnRelationWithDepartmentName(String departmentName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = """
            DELETE
            FROM works_on AS WO
            WHERE WO.project_id IN (SELECT P.project_id
                                    FROM project AS P
                                    WHERE P.project_name = ?1)""", nativeQuery = true)
    int deleteWorksOnRelationWithProjectName(String departmentName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = """
            DELETE
            FROM works_on AS WO
            WHERE WO.project_id IN (SELECT P.project_id
                                    FROM works_on AS WO,
                                         project AS P,
                                         "user" AS U,
                                         department AS D
                                    WHERE WO.project_id = P.project_id
                                      AND U.user_id = WO.user_id
                                      AND D.department_id = WO.department_id
                                      AND U.username = ?1
                                      AND D.department_name = ?2)""", nativeQuery = true)
    int deleteUsersProjectsBoundedToDepartment(String username, String departmentName);
}
