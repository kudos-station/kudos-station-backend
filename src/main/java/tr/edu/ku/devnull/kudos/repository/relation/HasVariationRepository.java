package tr.edu.ku.devnull.kudos.repository.relation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.ku.devnull.kudos.entity.relation.HasVariation;

@Repository
public interface HasVariationRepository extends JpaRepository<HasVariation, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO has_variation(kudos_id, kudos_variation_id) VALUES (?1, ?2)", nativeQuery = true)
    int insertHasVariation(Long kudosID, Long kudosVariationID);
}
