package tr.edu.ku.devnull.kudos.repository.util;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.ku.devnull.kudos.entity.util.Department;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "SELECT D.department_name FROM works_in AS WI, department AS D, \"user\" AS U" +
            " WHERE WI.department_id = D.department_id AND U.user_id = WI.user_id AND U.username = ?1", nativeQuery = true)
    List<String> getUsersDepartmentByUsername(String username);

    @Query(value = "SELECT D.department_name FROM department as D ", nativeQuery = true)
    List<String> getAllDepartmentNames();

    @Query(value = "SELECT D.department_id FROM department as D WHERE D.department_name = ?1 ", nativeQuery = true)
    Integer getDepartmentIDByName(String departmentName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO department(department_name, manager_id) VALUES (?1, ?2)", nativeQuery = true)
    int insertDepartment(String departmentName, Integer managerID);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM department as D WHERE D.department_name = ?1", nativeQuery = true)
    int deleteDepartmentByName(String departmentName);
}
