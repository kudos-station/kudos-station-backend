package tr.edu.ku.devnull.kudos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import tr.edu.ku.devnull.kudos.entity.Kudos;
import tr.edu.ku.devnull.kudos.response.KudosResponse;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface KudosMapper {

    KudosMapper INSTANCE = Mappers.getMapper(KudosMapper.class);

    KudosResponse entityToResponse(Kudos user);

    List<KudosResponse> entityListToResponseList(List<Kudos> user);
}
