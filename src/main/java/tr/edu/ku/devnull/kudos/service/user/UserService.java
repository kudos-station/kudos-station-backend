package tr.edu.ku.devnull.kudos.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.entity.user.User;
import tr.edu.ku.devnull.kudos.mapper.user.UserMapper;
import tr.edu.ku.devnull.kudos.repository.user.AuthorityRepository;
import tr.edu.ku.devnull.kudos.repository.util.DepartmentRepository;
import tr.edu.ku.devnull.kudos.repository.user.UserRepository;
import tr.edu.ku.devnull.kudos.response.user.UserProfileResponse;
import tr.edu.ku.devnull.kudos.response.user.UsernameListResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final DepartmentRepository departmentRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, DepartmentRepository departmentRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.departmentRepository = departmentRepository;
        this.userMapper = userMapper;
    }

    public UserProfileResponse getProfile() {
        UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getUserByUsername(u.getUsername());
        String currentUserRole = authorityRepository.getRoleByUsername(u.getUsername());

        UserProfileResponse userProfileResponse = userMapper.entityToUserProfile(user);
        String userDepartment = departmentRepository.getUsersDepartmentByUsername(u.getUsername());
        List<String> userProjects = departmentRepository.getUsersProjectsByUsername(u.getUsername());

        userProfileResponse.setAuthorities(currentUserRole);
        userProfileResponse.setDepartment(userDepartment);
        userProfileResponse.setProjects(userProjects);

        return userProfileResponse;
    }

    public UserProfileResponse getUser(String username) {

        UserProfileResponse userProfileResponse = userMapper.entityToUserProfile(userRepository.getUserByUsername(username));
        String userDepartment = departmentRepository.getUsersDepartmentByUsername(username);
        List<String> userProjects = departmentRepository.getUsersProjectsByUsername(username);
        String currentUserRole = authorityRepository.getRoleByUsername(username);

        userProfileResponse.setAuthorities(currentUserRole);
        userProfileResponse.setDepartment(userDepartment);
        userProfileResponse.setProjects(userProjects);

        return userProfileResponse;
    }

    public UsernameListResponse getUsersByProjectName(String projectName) {
        return UsernameListResponse.builder()
                .usernames(userRepository.getUsersByProjectName(projectName)
                        .stream()
                        .map(User::getUsername)
                        .collect(Collectors.toList()))
                .build();
    }

    public UsernameListResponse getUsersByDepartmentName(String departmentName) {
        return UsernameListResponse.builder()
                .usernames(userRepository.getUsersByDepartmentName(departmentName)
                        .stream()
                        .map(User::getUsername)
                        .collect(Collectors.toList()))
                .build();
    }
}
