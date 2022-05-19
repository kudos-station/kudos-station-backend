package tr.edu.ku.devnull.kudos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.ku.devnull.kudos.entity.Kudos;
import tr.edu.ku.devnull.kudos.entity.KudosVariation;

import java.util.List;

@Repository
public interface KudosRepository extends JpaRepository<Kudos, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO kudos(created_at, sender_username, recipient_username) VALUES" +
            " (NOW(), ?1, ?2)", nativeQuery = true)
    int insertKudos(String senderUsername, String recipientUsername);

    @Query(value = "SELECT * FROM kudos LIMIT 10", nativeQuery = true)
    List<Kudos> getRecentKudos();

    @Query(value = "SELECT * FROM kudos AS K WHERE K.recipient_username = ?1 LIMIT 10", nativeQuery = true)
    List<Kudos> getReceivedKudosByUsernameAndLimit(String username, String limit);

    @Query(value = "SELECT * FROM kudos AS K WHERE K.sender_username = ?1 LIMIT 10", nativeQuery = true)
    List<Kudos> getSentKudosByUsernameAndLimit(String username, String limit);

    @Query(value = "SELECT * FROM kudos AS K WHERE K.recipient_username = ?1 ORDER BY K.kudos_id DESC LIMIT 1", nativeQuery = true)
    Kudos getLastReceivedKudos(String username);
}
