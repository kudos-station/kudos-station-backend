package tr.edu.ku.devnull.kudos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import tr.edu.ku.devnull.kudos.dto.UserDto;
import tr.edu.ku.devnull.kudos.entity.User;
import tr.edu.ku.devnull.kudos.response.UserProfileResponse;
import tr.edu.ku.devnull.kudos.response.UserResponse;
import tr.edu.ku.devnull.kudos.response.UserRoleResponse;

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
