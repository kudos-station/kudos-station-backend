package tr.edu.ku.devnull.kudos.service.kudos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.dto.kudos.KudosIdentifierDto;
import tr.edu.ku.devnull.kudos.entity.kudos.Kudos;
import tr.edu.ku.devnull.kudos.entity.kudos.KudosVariation;
import tr.edu.ku.devnull.kudos.mapper.kudos.KudosMapper;
import tr.edu.ku.devnull.kudos.repository.kudos.KudosRepository;
import tr.edu.ku.devnull.kudos.repository.kudos.KudosVariationRepository;
import tr.edu.ku.devnull.kudos.repository.relation.HasVariationRepository;
import tr.edu.ku.devnull.kudos.repository.user.UserRepository;
import tr.edu.ku.devnull.kudos.response.kudos.KudosCountResponse;
import tr.edu.ku.devnull.kudos.response.kudos.KudosResponse;
import tr.edu.ku.devnull.kudos.response.kudos.KudosVariationResponse;
import tr.edu.ku.devnull.kudos.response.kudos.ScoreboardResponse;
import tr.edu.ku.devnull.kudos.response.user.UsernameListResponse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class KudosService {

    private final KudosRepository kudosRepository;

    private final UserRepository userRepository;

    private final KudosVariationRepository kudosVariationRepository;

    private final HasVariationRepository hasVariationRepository;

    private final KudosMapper kudosMapper;

    private final int IS_SUCCESSFUL = 1;

    @Autowired
    public KudosService(KudosRepository kudosRepository, UserRepository userRepository,
                        KudosVariationRepository kudosVariationRepository,
                        HasVariationRepository hasVariationRepository, KudosMapper kudosMapper) {
        this.kudosRepository = kudosRepository;
        this.userRepository = userRepository;
        this.kudosVariationRepository = kudosVariationRepository;
        this.hasVariationRepository = hasVariationRepository;
        this.kudosMapper = kudosMapper;
    }

    public boolean sendKudos(String sender, String recipient, String kudosVariation) {

        if (sender.equalsIgnoreCase(recipient)) {
            return false;
        }

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
        List<Kudos> kudosResultSet = kudosRepository.getRecentKudos();
        List<KudosIdentifierDto> kudosIdentifierDto = extractKudosData(kudosResultSet);

        return kudosMapper.identifierToResponse(kudosIdentifierDto);
    }

    public List<KudosResponse> getReceivedKudosByUsernameAndLimit(String username, String limit) {

        if (Integer.parseInt(limit) > 5) {
            limit = "5";
        }

        List<Kudos> kudosResultSet = kudosRepository.getReceivedKudosByUsernameAndLimit(username, Integer.valueOf(limit));
        List<KudosIdentifierDto> kudosIdentifierDto = extractKudosData(kudosResultSet);

        return kudosMapper.identifierToResponse(kudosIdentifierDto);
    }

    public List<KudosResponse> getSentKudosByUsernameAndLimit(String username, String limit) {

        if (Integer.parseInt(limit) > 5) {
            limit = "5";
        }

        List<Kudos> kudosResultSet = kudosRepository.getSentKudosByUsernameAndLimit(username, Integer.valueOf(limit));
        List<KudosIdentifierDto> kudosIdentifierDto = extractKudosData(kudosResultSet);

        return kudosMapper.identifierToResponse(kudosIdentifierDto);
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

    public UsernameListResponse getUsersWhoWorksInGivenDepartmentAndGotAllKudosVariations(String departmentName) {
        return UsernameListResponse.builder()
                .usernames(kudosRepository.getUsersWhoWorksInGivenDepartmentAndGotAllKudosVariations(departmentName))
                .build();
    }

    public List<KudosResponse> getKudosFromUsersThatWorkInAllProjectsInGivenDepartment(String departmentName) {
        List<Kudos> kudosResultSet = kudosRepository.getKudosFromUsersThatWorkInAllProjectsInGivenDepartment(departmentName);
        List<KudosIdentifierDto> kudosIdentifierDto = extractKudosData(kudosResultSet);

        return kudosMapper.identifierToResponse(kudosIdentifierDto);
    }

    private List<KudosIdentifierDto> extractKudosData(List<Kudos> kudosResultSet) {
        List<Object[]> variationResultSet = hasVariationRepository.getKudosVariationsOfIDs();
        List<KudosIdentifierDto> kudosIdentifierDto = kudosMapper.entityToIdentifierDto(kudosResultSet);


        for (KudosIdentifierDto d : kudosIdentifierDto) {
            for (Object[] o : variationResultSet) {
                if (d.getKudosID().equals((Integer) o[0])) {
                    d.setVariation((String) o[1]);
                }
            }
        }

        return kudosIdentifierDto;
    }

    public UsernameListResponse getUsersWhoSentAmountOfKudosButDidNotHasAmountOfKudos(String kudosVariation) {

        List<Object[]> resultSet = kudosRepository
                .getUsersWhoSentAmountOfKudosButDidNotHasAmountOfKudos(kudosVariation);

        return UsernameListResponse.builder()
                .usernames(resultSet.stream().map(e -> (String) e[0]).collect(Collectors.toList()))
                .build();
    }

    public List<ScoreboardResponse> getScoreboard() {
        List<Object[]> entrySet = kudosRepository.getScoreboard();
        List<ScoreboardResponse> resultSet = new ArrayList<>();

        for(Object[] o: entrySet) {
            resultSet.add(ScoreboardResponse.builder().username((String) o[0]).totalCount((BigInteger) o[1]).build());
        }

        return resultSet;
    }

    public KudosCountResponse getKudosCount(String username) {
        return KudosCountResponse.builder()
                .totalCount(kudosRepository.getKudosCountByUsername(username))
                .build();
    }

    // This method runs periodically for generating fake data.
    @Scheduled(fixedRate = 900000)
    public void sendKudosPeriodically() {
        List<Integer> userIndices = new ArrayList<>();

        Collections.addAll(userIndices, 1, 2, 4, 5, 6, 7);

        for (int i = 20; i < 168; i++) {
            userIndices.add(i);
        }

        String sender = userRepository.getUsernameByUserId(userIndices.get(new Random().nextInt(userIndices.size())));

        String recipient = userRepository.getUsernameByUserId(userIndices.get(new Random().nextInt(userIndices.size())));

        List<KudosVariation> kudosVariationList = kudosVariationRepository.getKudosVariations();

        String kudosVariation = kudosVariationList
                .get(new Random().nextInt(kudosVariationList.size()))
                .getKudosVariationName();

        if (sender.equalsIgnoreCase(recipient)) {
            return;
        }

        int status = kudosRepository.insertKudos(sender, recipient);

        if (status == IS_SUCCESSFUL) {
            Kudos lastKudos = kudosRepository.getLastReceivedKudos(recipient);

            if (lastKudos != null) {
                KudosVariation kudosVar = kudosVariationRepository.getKudosVariation(kudosVariation);
                hasVariationRepository.insertHasVariation(lastKudos.getKudosID(), kudosVar.getKudosVariationID());
            }
        }

    }
}
