package tr.edu.ku.devnull.kudos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.dto.UserDto;
import tr.edu.ku.devnull.kudos.mapper.UserMapper;
import tr.edu.ku.devnull.kudos.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> getAllUsers() {
        return userMapper.entityToDtoList(userRepository.getAllUsers());
    }
}
