package tr.edu.ku.devnull.kudos.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import tr.edu.ku.devnull.kudos.dto.user.UserDto;
import tr.edu.ku.devnull.kudos.entity.user.User;
import tr.edu.ku.devnull.kudos.response.user.UserProfileResponse;
import tr.edu.ku.devnull.kudos.response.user.UserResponse;
import tr.edu.ku.devnull.kudos.response.user.UserRoleResponse;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto entityToDto(User user);

    UserResponse dtoToResponse(UserDto userDto);

    List<UserDto> entityToDtoList(List<User> user);

    List<UserResponse> dtoToResponseList(List<UserDto> userDto);

    UserRoleResponse entityToUserRole(User user);

    UserProfileResponse entityToUserProfile(User user);
}
