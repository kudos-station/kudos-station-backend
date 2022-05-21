package tr.edu.ku.devnull.kudos.service.kudos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.entity.kudos.Kudos;
import tr.edu.ku.devnull.kudos.entity.kudos.KudosVariation;
import tr.edu.ku.devnull.kudos.mapper.kudos.KudosMapper;
import tr.edu.ku.devnull.kudos.repository.relation.HasVariationRepository;
import tr.edu.ku.devnull.kudos.repository.kudos.KudosRepository;
import tr.edu.ku.devnull.kudos.repository.kudos.KudosVariationRepository;
import tr.edu.ku.devnull.kudos.response.kudos.KudosResponse;
import tr.edu.ku.devnull.kudos.response.kudos.KudosVariationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KudosService {

    private final KudosRepository kudosRepository;

    private final KudosVariationRepository kudosVariationRepository;

    private final HasVariationRepository hasVariationRepository;

    private final KudosMapper kudosMapper;

    private final int IS_SUCCESSFUL = 1;

    @Autowired
    public KudosService(KudosRepository kudosRepository, KudosVariationRepository kudosVariationRepository,
                        HasVariationRepository hasVariationRepository, KudosMapper kudosMapper) {
        this.kudosRepository = kudosRepository;
        this.kudosVariationRepository = kudosVariationRepository;
        this.hasVariationRepository = hasVariationRepository;
        this.kudosMapper = kudosMapper;
    }

    public boolean sendKudos(String sender, String recipient, String kudosVariation) {

        int status = kudosRepository.insertKudos(sender, recipient);

        if (status == IS_SUCCESSFUL) {
            Kudos lastKudos = kudosRepository.getLastReceivedKudos(recipient);
            if (lastKudos != null) {
                KudosVariation kudosVar = kudosVariationRepository.getKudosVariation(kudosVariation);
                return hasVariationRepository.insertHasVariation(lastKudos.getKudosID(), kudosVar.getKudosVariationID()) == IS_SUCCESSFUL;
            } else {
                return false;
            }
        }

        return false;
    }

    public List<KudosResponse> getRecentKudos() {
        return kudosMapper.entityListToResponseList(kudosRepository.getRecentKudos());
    }

    public List<KudosResponse> getReceivedKudosByUsernameAndLimit(String username, String limit) {
        return kudosMapper.entityListToResponseList(kudosRepository.getReceivedKudosByUsernameAndLimit(username, limit));
    }

    public List<KudosResponse> getSentKudosByUsernameAndLimit(String username, String limit) {
        return kudosMapper.entityListToResponseList(kudosRepository.getSentKudosByUsernameAndLimit(username, limit));
    }

    public KudosVariationResponse getKudosVariations() {
        List<KudosVariation> kudosVariations = kudosVariationRepository.getKudosVariations();
        return KudosVariationResponse.builder()
                .kudosVariations(
                        kudosVariations
                                .stream()
                                .map(KudosVariation::getKudosVariationName)
                                .collect(Collectors.toList()))
                .build();
    }
}
