package tr.edu.ku.devnull.kudos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.dto.CreateUserDto;
import tr.edu.ku.devnull.kudos.dto.UpdateUserRoleDto;
import tr.edu.ku.devnull.kudos.mapper.UserMapper;
import tr.edu.ku.devnull.kudos.repository.AuthorityRepository;
import tr.edu.ku.devnull.kudos.repository.UserRepository;

@Service
public class UserEditService {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final UserMapper userMapper;

    private final int IS_SUCCESSFUL = 1;

    @Autowired
    public UserEditService(UserRepository userRepository, AuthorityRepository authorityRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.userMapper = userMapper;
    }

    public boolean createUser(CreateUserDto createUserDto) {
        int statusForUser = userRepository.createUser(createUserDto);
        int statusForRole = authorityRepository.insertUserRole(createUserDto);

        return statusForRole == IS_SUCCESSFUL && statusForUser == IS_SUCCESSFUL;
    }

    public boolean updateUserRole(UpdateUserRoleDto updateUserRoleDto) {

        if (!(updateUserRoleDto.getAuthority().equals("ROLE_ADMIN") ||
                updateUserRoleDto.getAuthority().equals("ROLE_USER"))) {
            return false;
        }

        int statusForUser = authorityRepository.updateUserRole(updateUserRoleDto);

        return statusForUser == IS_SUCCESSFUL;
    }

    public boolean deleteUser(String nickname) {
        int statusForRole = authorityRepository.deleteUserRoleByNickname(nickname);
        int statusForUser = userRepository.deleteUserByNickname(nickname);

        return statusForRole == IS_SUCCESSFUL && statusForUser == IS_SUCCESSFUL;
    }
}
