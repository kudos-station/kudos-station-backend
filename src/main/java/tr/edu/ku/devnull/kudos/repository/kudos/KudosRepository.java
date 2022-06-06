package tr.edu.ku.devnull.kudos.repository.kudos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.ku.devnull.kudos.entity.kudos.Kudos;
import tr.edu.ku.devnull.kudos.response.kudos.ScoreboardResponse;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface KudosRepository extends JpaRepository<Kudos, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO kudos(created_at, sender_username, recipient_username) VALUES" +
            " (NOW(), ?1, ?2)", nativeQuery = true)
    int insertKudos(String senderUsername, String recipientUsername);

    @Query(value = "SELECT * FROM kudos ORDER BY created_at DESC LIMIT 10", nativeQuery = true)
    List<Kudos> getRecentKudos();

    @Query(value = "SELECT * FROM kudos AS K WHERE K.recipient_username = ?1 ORDER BY created_at DESC LIMIT ?2", nativeQuery = true)
    List<Kudos> getReceivedKudosByUsernameAndLimit(String username, Integer limit);

    @Query(value = "SELECT * FROM kudos AS K WHERE K.sender_username = ?1 ORDER BY created_at DESC LIMIT ?2", nativeQuery = true)
    List<Kudos> getSentKudosByUsernameAndLimit(String username, Integer limit);

    @Query(value = "SELECT * FROM kudos AS K WHERE K.recipient_username = ?1 ORDER BY K.kudos_id DESC LIMIT 1", nativeQuery = true)
    Kudos getLastReceivedKudos(String username);

    // Kaan's Query.
    @Query(value = """
            SELECT U.username
            FROM "user" AS U
            WHERE U.username IN (
                SELECT U.username
                FROM "user" AS U,
                     department AS D,
                     works_in AS WI
                WHERE U.user_id = WI.user_id
                  AND D.department_id = WI.department_id
                  AND D.department_name = ?1
            )
              AND U.username IN (
                SELECT kudos_data_of_users.recipient_username
                FROM (SELECT K.recipient_username, COUNT(DISTINCT KV.kudos_variation_id) AS total_count
                      FROM kudos AS K,
                           kudos_variation AS KV,
                           has_variation AS HV
                      WHERE K.kudos_id = HV.kudos_id
                        AND KV.kudos_variation_id = HV.kudos_variation_id
                      GROUP BY K.recipient_username) AS kudos_data_of_users
                WHERE kudos_data_of_users.total_count = (SELECT COUNT(DISTINCT KV.kudos_variation_id) FROM kudos_variation AS KV))
            LIMIT 3""", nativeQuery = true)
    List<String> getUsersWhoWorksInGivenDepartmentAndGotAllKudosVariations(String departmentName);

    // Can's Query.
    @Query(value = """
            SELECT *
            FROM kudos AS k
            WHERE k.recipient_username IN (SELECT u.username
                                          FROM "user" AS u,
                                               department AS d
                                          WHERE d.department_name = ?1
                                            AND NOT EXISTS(SELECT p.project_id
                                                           FROM project AS p
                                                           WHERE p.department_id = d.department_id
                                                           EXCEPT
                                                           (SELECT wo.project_id
                                                            FROM works_on AS wo
                                                            WHERE u.user_id = wo.user_id)))
            ORDER BY created_at DESC
            LIMIT 3""", nativeQuery = true)
    List<Kudos> getKudosFromUsersThatWorkInAllProjectsInGivenDepartment(String departmentName);

    // Omer's Query.
    @Query(value = """
            SELECT u.username
            FROM "user" AS u,
                 kudos AS k,
                 has_variation AS hv,
                 kudos_variation AS kv
            WHERE u.username = k.sender_username
              AND k.kudos_id = hv.kudos_id
              AND hv.kudos_variation_id = kv.kudos_variation_id
              AND kv.kudos_variation_name = ?1
              AND u.username NOT IN (SELECT u2.sender_username
                                     FROM kudos AS u2
                                     GROUP BY u2.sender_username
                                     HAVING COUNT(*) < 10)
            GROUP BY u.username
            HAVING COUNT(*) > 2
            LIMIT 3""", nativeQuery = true)
    List<Object[]> getUsersWhoSentAmountOfKudosButDidNotHasAmountOfKudos(String kudosVariation);

    @Query(value = """
            SELECT k.recipient_username, COUNT(k.recipient_username) as total_count
            FROM kudos AS k
            GROUP BY k.recipient_username
            ORDER BY total_count DESC
            LIMIT 10""", nativeQuery = true)
    List<Object[]> getScoreboard();

    @Query(value = """
            SELECT COUNT(k.recipient_username) as total_count
            FROM "user" as U, kudos as K
            WHERE U.username = K.recipient_username AND U.username = ?1""", nativeQuery = true)
    BigInteger getKudosCountByUsername(String username);
}