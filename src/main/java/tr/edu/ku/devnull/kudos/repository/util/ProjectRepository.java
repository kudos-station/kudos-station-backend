package tr.edu.ku.devnull.kudos.repository.util;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.ku.devnull.kudos.entity.util.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(value = "SELECT P.project_name FROM works_on AS WO, project AS P, \"user\" AS U" +
            " WHERE WO.project_id = P.project_id AND U.user_id = WO.user_id AND U.username = ?1", nativeQuery = true)
    List<String> getUsersProjectsByUsername(String username);

    @Query(value = "SELECT P.project_name FROM project as P ", nativeQuery = true)
    List<String> getAllProjectNames();

    @Query(value = "SELECT P.project_id FROM project as P WHERE P.project_name = ?1 ", nativeQuery = true)
    Integer getProjectIDByName(String projectName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO project(project_name, department_id) VALUES (?1, ?2)", nativeQuery = true)
    int insertProject(String projectName, Integer departmentID);
}
