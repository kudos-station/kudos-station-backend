package tr.edu.ku.devnull.kudos.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ku.devnull.kudos.dto.user.CreateUserDto;
import tr.edu.ku.devnull.kudos.dto.user.UpdateUserRoleDto;
import tr.edu.ku.devnull.kudos.repository.relation.WorksInRepository;
import tr.edu.ku.devnull.kudos.repository.relation.WorksOnRepository;
import tr.edu.ku.devnull.kudos.repository.user.AuthorityRepository;
import tr.edu.ku.devnull.kudos.repository.user.UserRepository;

@Service
public class UserEditService {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final WorksInRepository worksInRepository;

    private final WorksOnRepository worksOnRepository;

    private final int IS_SUCCESSFUL = 1;

    @Autowired
    public UserEditService(UserRepository userRepository, AuthorityRepository authorityRepository,
                           WorksInRepository worksInRepository, WorksOnRepository worksOnRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.worksInRepository = worksInRepository;
        this.worksOnRepository = worksOnRepository;
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

    public boolean deleteUser(String username) {
        worksOnRepository.deleteWorksOnRelation(username);
        worksInRepository.deleteWorksInRelation(username);
        authorityRepository.deleteUserRoleByUsername(username);

        int statusForUser = userRepository.deleteUserByUsername(username);

        return statusForUser == IS_SUCCESSFUL;
    }
}
