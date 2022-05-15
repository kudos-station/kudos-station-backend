package tr.edu.ku.devnull.kudos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.dto.KudosDto;
import tr.edu.ku.devnull.kudos.mapper.KudosMapper;
import tr.edu.ku.devnull.kudos.repository.KudosRepository;
import tr.edu.ku.devnull.kudos.response.KudosResponse;

import java.util.List;

@Service
public class KudosService {

    private final KudosRepository kudosRepository;

    private final KudosMapper kudosMapper;

    private final int IS_SUCCESSFUL = 1;

    @Autowired
    public KudosService(KudosRepository kudosRepository, KudosMapper kudosMapper) {
        this.kudosRepository = kudosRepository;
        this.kudosMapper = kudosMapper;
    }

    public boolean sendKudos(String sender, String recipient, KudosDto kudosDto) {

        if (kudosDto == null && kudosDto.getContent() == null) return false;
        if (kudosDto.getContent() == null) return false;
        if (kudosDto.getContent().length() < 10) return false;

        int status = kudosRepository.insertKudos(sender, recipient, kudosDto.getContent());

        return status == IS_SUCCESSFUL;
    }

    public List<KudosResponse> getRecentKudos() {
        return kudosMapper.entityListToResponseList(kudosRepository.getRecentKudos());
    }

    public List<KudosResponse> getReceivedKudosByNicknameAndLimit(String nickname, String limit) {
        return kudosMapper.entityListToResponseList(kudosRepository.getRecievedKudosByNicknameAndLimit(nickname, limit));
    }

    public List<KudosResponse> getSentKudosByNicknameAndLimit(String nickname, String limit) {
        return kudosMapper.entityListToResponseList(kudosRepository.getSentKudosByNicknameAndLimit(nickname, limit));
    }
}