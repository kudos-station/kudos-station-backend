package tr.edu.ku.devnull.kudos.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.ku.devnull.kudos.dto.user.CreateUserDto;
import tr.edu.ku.devnull.kudos.entity.user.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM \"user\" as U WHERE U.username = ?1", nativeQuery = true)
    User getUserByUsername(String username);

    @Query(value = "SELECT * FROM works_on AS WO, project AS P, \"user\" AS U" +
            " WHERE WO.project_id = P.project_id AND U.user_id = WO.user_id AND P.project_name = ?1", nativeQuery = true)
    List<User> getUsersByProjectName(String projectName);

    @Query(value = "SELECT * FROM works_in AS WI, \"user\" AS U, department AS D" +
            " WHERE WI.user_id = U.user_id AND D.department_name = ?1", nativeQuery = true)
    List<User> getUsersByDepartmentName(String departmentName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO \"user\"(first_name, last_name, username, password, enabled)" +
            " VALUES (:#{#createUserDto.firstName}, :#{#createUserDto.lastName}, :#{#createUserDto.username}, " +
            " :#{#createUserDto.password}, true)", nativeQuery = true)
    int createUser(@Param("createUserDto") CreateUserDto createUserDto);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM \"user\" WHERE username = ?1", nativeQuery = true)
    int deleteUserByUsername(String username);
}
