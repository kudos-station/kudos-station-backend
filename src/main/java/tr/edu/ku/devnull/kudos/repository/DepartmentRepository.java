package tr.edu.ku.devnull.kudos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tr.edu.ku.devnull.kudos.entity.Department;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "SELECT D.department_name FROM works_in AS WI, department AS D, \"user\" AS U" +
            " WHERE WI.department_id = D.department_id AND U.user_id = WI.user_id AND U.username = ?1", nativeQuery = true)
    String getUsersDepartmentByUsername(String username);

    @Query(value = "SELECT P.project_name FROM works_on AS WO, project AS P, \"user\" AS U" +
            " WHERE WO.project_id = P.project_id AND U.user_id = WO.user_id AND U.username = ?1", nativeQuery = true)
    List<String> getUsersProjectsByUsername(String username);
}
