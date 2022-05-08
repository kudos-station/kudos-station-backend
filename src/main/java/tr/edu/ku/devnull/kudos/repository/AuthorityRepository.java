package tr.edu.ku.devnull.kudos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.ku.devnull.kudos.dto.CreateUserDto;
import tr.edu.ku.devnull.kudos.dto.UpdateUserRoleDto;
import tr.edu.ku.devnull.kudos.entity.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    @Query(value = "SELECT A.authority FROM authorities as A WHERE A.nickname = ?1", nativeQuery = true)
    String getRoleByUsername(String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO authorities(nickname, authority) VALUES (:#{#createUserDto.nickname}," +
            " :#{#createUserDto.authority})", nativeQuery = true)
    int insertUserRole(@Param("createUserDto") CreateUserDto createUserDto);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE authorities SET authority = :#{#updateUserRoleDto.authority} WHERE nickname =" +
            " :#{#updateUserRoleDto.nickname}", nativeQuery = true)
    int updateUserRole(@Param("updateUserRoleDto") UpdateUserRoleDto updateUserRoleDto);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM authorities WHERE nickname = ?1", nativeQuery = true)
    int deleteUserRoleByNickname(String nickname);
}
