package tr.edu.ku.devnull.kudos.repository.kudos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tr.edu.ku.devnull.kudos.entity.kudos.KudosVariation;

import java.util.List;

@Repository
public interface KudosVariationRepository extends JpaRepository<KudosVariation, Long> {
    @Query(value = "SELECT * FROM kudos_variation AS KV WHERE KV.kudos_variation_name = ?1", nativeQuery = true)
    KudosVariation getKudosVariation(String kudosVariation);

    @Query(value = "SELECT * FROM kudos_variation", nativeQuery = true)
    List<KudosVariation> getKudosVariations();
}
