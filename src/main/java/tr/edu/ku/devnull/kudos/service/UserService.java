package tr.edu.ku.devnull.kudos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.dto.UserDto;
import tr.edu.ku.devnull.kudos.entity.User;
import tr.edu.ku.devnull.kudos.mapper.UserMapper;
import tr.edu.ku.devnull.kudos.repository.AuthorityRepository;
import tr.edu.ku.devnull.kudos.repository.UserRepository;
import tr.edu.ku.devnull.kudos.response.UserProfileResponse;
import tr.edu.ku.devnull.kudos.response.UserRoleResponse;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
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
        String currentUserRole = authorityRepository.getRoleByUsername(u.getUsername());

        UserRoleResponse userRoleResponse = userMapper.entityToUserRole(user);
        userRoleResponse.setAuthorities(currentUserRole);

        return userRoleResponse;
    }

    public UserProfileResponse getUser(String nickname) {
        return userMapper.entityToUserProfile(userRepository.getUserByUsername(nickname));
    }
}
