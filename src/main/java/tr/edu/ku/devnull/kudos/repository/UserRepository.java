package tr.edu.ku.devnull.kudos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.ku.devnull.kudos.dto.CreateUserDto;
import tr.edu.ku.devnull.kudos.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<User> getAllUsers();

    @Query(value = "SELECT * FROM users as U WHERE U.nickname = ?1", nativeQuery = true)
    User getUserByUsername(String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO users(first_name, last_name, nickname, department, password, enabled)" +
            " VALUES (:#{#createUserDto.firstName}, :#{#createUserDto.lastName}, :#{#createUserDto.nickname}, :#{#createUserDto.department}," +
            " :#{#createUserDto.password}, true)", nativeQuery = true)
    int createUser(@Param("createUserDto") CreateUserDto createUserDto);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM users WHERE nickname = ?1", nativeQuery = true)
    int deleteUserByNickname(String nickname);
}
