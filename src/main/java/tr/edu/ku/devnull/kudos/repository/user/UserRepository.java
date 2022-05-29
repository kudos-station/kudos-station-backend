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

    @Query(value = "SELECT U.user_id FROM \"user\" as U WHERE U.username = ?1", nativeQuery = true)
    Integer getUserIdByUsername(String username);

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

    // Irmak's Query.
    @Query(value = """
            WITH user_and_count
                     AS (SELECT kudos.recipient_username AS username, COUNT(*) AS n_kudos
                         FROM kudos
                         WHERE kudos.kudos_id IN (SELECT H.kudos_id
                                                  FROM has_variation AS H,
                                                       kudos_variation AS K
                                                  WHERE K.kudos_variation_name = ?1
                                                    AND H.kudos_variation_id = K.kudos_variation_id)
                         GROUP BY kudos.recipient_username
                )
            SELECT u.username AS username, p.project_name AS project
            FROM "user" AS u,
                 project AS p,
                 works_on AS w
            WHERE u.user_id = w.user_id
              AND w.project_id = p.project_id
              AND u.username = (SELECT username FROM user_and_count WHERE n_kudos = (SELECT MAX(n_kudos) FROM user_and_count))
            LIMIT 1""", nativeQuery = true)
    List<Object[]> getUserWhoGotMostOfGivenKudosVariationAndItsCurrentProject(String kudosVariationName);

    @Query(value = """
            WITH usernames AS
                     (
                         SELECT DISTINCT a.username
                         FROM (SELECT u.username AS username
                               FROM "user" AS u
                               WHERE NOT EXISTS(SELECT DISTINCT K.kudos_variation_name
                                                FROM kudos_variation AS K
                                                WHERE K.kudos_variation_name NOT IN (SELECT DISTINCT K1.kudos_variation_name
                                                                                     FROM kudos,
                                                                                          kudos_variation AS K1,
                                                                                          has_variation AS H
                                                                                     WHERE kudos.recipient_username = u.username
                                                                                       AND kudos.kudos_id = H.kudos_id
                                                                                       AND H.kudos_variation_id = K1.kudos_variation_id))
                              ) AS a
                                  INNER JOIN
                                  (SELECT kudos.sender_username AS username FROM kudos) AS b
                                  ON a.username = b.username
                     )
            SELECT u1.username, u1.first_name, u1.last_name
            FROM "user" AS u1,
                 project AS p,
                 works_on AS w
            WHERE u1.user_id = w.user_id
              AND w.project_id = p.project_id
              AND p.project_name = ?1
              AND u1.username IN (SELECT DISTINCT username FROM usernames)
            LIMIT 1""", nativeQuery = true)
    List<Object[]> getUserWhoWorksInGivenProjectAndReceivedAllKudosVariationsAndSentAnyKudos(String projectName);
}
