package tr.edu.ku.devnull.kudos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.ku.devnull.kudos.entity.Kudos;

import java.util.List;

@Repository
public interface KudosRepository extends JpaRepository<Kudos, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO kudos(created_at, sender_nickname, recipient_nickname, content) VALUES" +
            " (NOW(), ?1, ?2, ?3)", nativeQuery = true)
    int insertKudos(String sender, String recipient, String content);

    @Query(value = "SELECT * FROM kudos LIMIT 10", nativeQuery = true)
    List<Kudos> getRecentKudos();

    @Query(value = "SELECT * FROM kudos AS K WHERE K.recipient_nickname = ?1 LIMIT 10", nativeQuery = true)
    List<Kudos> getRecievedKudosByNicknameAndLimit(String nickname, String limit);

    @Query(value = "SELECT * FROM kudos AS K WHERE K.sender_nickname = ?1 LIMIT 10", nativeQuery = true)
    List<Kudos> getSentKudosByNicknameAndLimit(String nickname, String limit);
}
