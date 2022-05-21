package tr.edu.ku.devnull.kudos.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.ku.devnull.kudos.dto.user.CreateUserDto;
import tr.edu.ku.devnull.kudos.dto.user.UpdateUserRoleDto;
import tr.edu.ku.devnull.kudos.entity.user.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    @Query(value = "SELECT A.authority FROM authority as A WHERE A.username = ?1", nativeQuery = true)
    String getRoleByUsername(String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO authority(username, authority) VALUES (:#{#createUserDto.username}," +
            " :#{#createUserDto.authority})", nativeQuery = true)
    int insertUserRole(@Param("createUserDto") CreateUserDto createUserDto);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE authority SET authority = :#{#updateUserRoleDto.authority} WHERE username =" +
            " :#{#updateUserRoleDto.username}", nativeQuery = true)
    int updateUserRole(@Param("updateUserRoleDto") UpdateUserRoleDto updateUserRoleDto);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM authority WHERE username = ?1", nativeQuery = true)
    int deleteUserRoleByUsername(String username);
}
