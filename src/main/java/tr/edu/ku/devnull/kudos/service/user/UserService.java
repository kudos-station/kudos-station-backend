package tr.edu.ku.devnull.kudos.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.entity.user.User;
import tr.edu.ku.devnull.kudos.mapper.user.UserMapper;
import tr.edu.ku.devnull.kudos.repository.user.AuthorityRepository;
import tr.edu.ku.devnull.kudos.repository.user.UserRepository;
import tr.edu.ku.devnull.kudos.repository.util.DepartmentRepository;
import tr.edu.ku.devnull.kudos.repository.util.ProjectRepository;
import tr.edu.ku.devnull.kudos.response.user.UserIdentifierResponse;
import tr.edu.ku.devnull.kudos.response.user.UserProfileResponse;
import tr.edu.ku.devnull.kudos.response.user.UsernameAndProjectResponse;
import tr.edu.ku.devnull.kudos.response.user.UsernameListResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final DepartmentRepository departmentRepository;

    private final ProjectRepository projectRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, DepartmentRepository departmentRepository, ProjectRepository projectRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
        this.userMapper = userMapper;
    }

    public UserProfileResponse getProfile() {
        UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getUserByUsername(u.getUsername());
        String currentUserRole = authorityRepository.getRoleByUsername(u.getUsername());

        UserProfileResponse userProfileResponse = userMapper.entityToUserProfile(user);
        List<String> userDepartment = departmentRepository.getUsersDepartmentByUsername(u.getUsername());
        List<String> userProjects = projectRepository.getUsersProjectsByUsername(u.getUsername());

        userProfileResponse.setAuthorities(currentUserRole);
        userProfileResponse.setDepartment(userDepartment);
        userProfileResponse.setProjects(userProjects);

        return userProfileResponse;
    }

    public UserProfileResponse getUser(String username) {

        UserProfileResponse userProfileResponse = userMapper.entityToUserProfile(userRepository.getUserByUsername(username));
        List<String> userDepartment = departmentRepository.getUsersDepartmentByUsername(username);
        List<String> userProjects = projectRepository.getUsersProjectsByUsername(username);
        String currentUserRole = authorityRepository.getRoleByUsername(username);

        userProfileResponse.setAuthorities(currentUserRole);
        userProfileResponse.setDepartment(userDepartment);
        userProfileResponse.setProjects(userProjects);

        return userProfileResponse;
    }

    public Integer getUserIdByUsername(String username) {
        return userRepository.getUserIdByUsername(username);
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

    public UsernameAndProjectResponse getUserWhoGotMostOfGivenKudosVariationAndItsCurrentProject(String kudosVariationName) {
        List<Object[]> resultSet = userRepository
                .getUserWhoGotMostOfGivenKudosVariationAndItsCurrentProject(kudosVariationName);


        return UsernameAndProjectResponse.builder()
                .username((String) resultSet.get(0)[0])
                .project(resultSet.stream().map(e -> (String) e[1]).collect(Collectors.toList()))
                .build();
    }

    public UserIdentifierResponse getUserWhoWorksInGivenProjectAndReceivedAllKudosVariationsAndSentAnyKudos(String projectName) {
        List<Object[]> resultSet = userRepository
                .getUserWhoWorksInGivenProjectAndReceivedAllKudosVariationsAndSentAnyKudos(projectName);

        return UserIdentifierResponse.builder()
                .usernames(resultSet.stream().map(e -> (String) e[0]).collect(Collectors.toList()))
                .firstNames(resultSet.stream().map(e -> (String) e[1]).collect(Collectors.toList()))
                .lastNames(resultSet.stream().map(e -> (String) e[2]).collect(Collectors.toList()))
                .build();
    }
}
