package tr.edu.ku.devnull.kudos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.dto.KudosDto;
import tr.edu.ku.devnull.kudos.mapper.UserMapper;
import tr.edu.ku.devnull.kudos.repository.KudosRepository;

@Service
public class KudosService {

    private final KudosRepository kudosRepository;

    private final UserMapper userMapper;

    private final int IS_SUCCESSFUL = 1;

    @Autowired
    public KudosService(KudosRepository kudosRepository, UserMapper userMapper) {
        this.kudosRepository = kudosRepository;
        this.userMapper = userMapper;
    }

    public boolean sendKudos(String sender, String recipient, KudosDto kudosDto) {

        if (kudosDto == null && kudosDto.getContent() == null) return false;
        if (kudosDto.getContent() == null) return false;
        if (kudosDto.getContent().length() < 10) return false;

        int status = kudosRepository.insertKudos(sender, recipient, kudosDto.getContent());

        return status == IS_SUCCESSFUL;
    }
}
