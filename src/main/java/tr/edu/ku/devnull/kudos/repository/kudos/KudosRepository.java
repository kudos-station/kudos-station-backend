package tr.edu.ku.devnull.kudos.repository.kudos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.ku.devnull.kudos.entity.kudos.Kudos;

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

    @Query(value = "SELECT * FROM kudos AS K WHERE K.recipient_username = ?1 LIMIT 10", nativeQuery = true)
    List<Kudos> getReceivedKudosByUsernameAndLimit(String username, String limit);

    @Query(value = "SELECT * FROM kudos AS K WHERE K.sender_username = ?1 LIMIT 10", nativeQuery = true)
    List<Kudos> getSentKudosByUsernameAndLimit(String username, String limit);

    @Query(value = "SELECT * FROM kudos AS K WHERE K.recipient_username = ?1 ORDER BY K.kudos_id DESC LIMIT 1", nativeQuery = true)
    Kudos getLastReceivedKudos(String username);

    @Query(value = "SELECT U.username\n" +
            "FROM \"user\" AS U\n" +
            "WHERE U.username IN (\n" +
            "    SELECT U.username\n" +
            "    FROM \"user\" AS U,\n" +
            "         department AS D,\n" +
            "         works_in AS WI\n" +
            "    WHERE U.user_id = WI.user_id\n" +
            "      AND D.department_id = WI.department_id\n" +
            "      AND D.department_name = ?1\n" +
            ")\n" +
            "  AND U.username IN (\n" +
            "    SELECT kudos_data_of_users.recipient_username\n" +
            "    FROM (SELECT K.recipient_username, COUNT(DISTINCT KV.kudos_variation_id) AS total_count\n" +
            "          FROM kudos AS K,\n" +
            "               kudos_variation AS KV,\n" +
            "               has_variation AS HV\n" +
            "          WHERE K.kudos_id = HV.kudos_id\n" +
            "            AND KV.kudos_variation_id = HV.kudos_variation_id\n" +
            "          GROUP BY K.recipient_username) AS kudos_data_of_users\n" +
            "    WHERE kudos_data_of_users.total_count = (SELECT COUNT(DISTINCT KV.kudos_variation_id) FROM kudos_variation AS KV))", nativeQuery = true)
    List<String> getUsersWhoWorksInGivenDepartmentAndGotAllKudosVariations(String departmentName);
}
