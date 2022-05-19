package tr.edu.ku.devnull.kudos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.ku.devnull.kudos.dto.CreateUserDto;
import tr.edu.ku.devnull.kudos.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM \"user\" as U WHERE U.username = ?1", nativeQuery = true)
    User getUserByUsername(String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO \"user\"(first_name, last_name, username, password, enabled)" +
            " VALUES (:#{#createUserDto.firstName}, :#{#createUserDto.lastName}, :#{#createUserDto.username}, " +
            " :#{#createUserDto.password}, true)", nativeQuery = true)
    int createUser(@Param("createUserDto") CreateUserDto createUserDto);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM \"user\" WHERE username = ?1", nativeQuery = true)
    int deleteUserByUsername(String username);
}
