package tr.edu.ku.devnull.kudos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.dto.CreateUserDto;
import tr.edu.ku.devnull.kudos.dto.UpdateUserRoleDto;
import tr.edu.ku.devnull.kudos.dto.UserDto;
import tr.edu.ku.devnull.kudos.entity.User;
import tr.edu.ku.devnull.kudos.mapper.UserMapper;
import tr.edu.ku.devnull.kudos.repository.UserRepository;
import tr.edu.ku.devnull.kudos.response.UserProfileResponse;
import tr.edu.ku.devnull.kudos.response.UserRoleResponse;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final int IS_SUCCESSFUL = 1;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> getAllUsers() {
        return userMapper.entityToDtoList(userRepository.getAllUsers());
    }

    public UserProfileResponse getProfile() {
        UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getUserByUsername(u.getUsername());

        return userMapper.entityToUserProfile(user);
    }

    public UserRoleResponse getCurrentUserWithAuthorities() {
        UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getUserByUsername(u.getUsername());
        String currentUserRole = userRepository.getRoleByUsername(u.getUsername());

        UserRoleResponse userRoleResponse = userMapper.entityToUserRole(user);
        userRoleResponse.setAuthorities(currentUserRole);

        return userRoleResponse;
    }

    public UserProfileResponse getUser(String nickname) {
        return userMapper.entityToUserProfile(userRepository.getUserByUsername(nickname));
    }

    public boolean createUser(CreateUserDto createUserDto) {
        int statusForUser = userRepository.createUser(createUserDto);
        int statusForRole = userRepository.insertUserRole(createUserDto);

        return statusForRole == IS_SUCCESSFUL && statusForUser == IS_SUCCESSFUL;
    }

    public boolean updateUserRole(UpdateUserRoleDto updateUserRoleDto) {

        if (!(updateUserRoleDto.getAuthority().equals("ROLE_ADMIN") ||
                updateUserRoleDto.getAuthority().equals("ROLE_USER"))) {
            return false;
        }

        int statusForUser = userRepository.updateUserRole(updateUserRoleDto);

        return statusForUser == IS_SUCCESSFUL;
    }

    public boolean deleteUser(String nickname) {
        int statusForRole = userRepository.deleteUserRoleByNickname(nickname);
        int statusForUser = userRepository.deleteUserByNickname(nickname);

        return statusForRole == IS_SUCCESSFUL && statusForUser == IS_SUCCESSFUL;
    }
}
