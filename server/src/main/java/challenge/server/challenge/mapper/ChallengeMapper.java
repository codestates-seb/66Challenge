package challenge.server.challenge.mapper;

import challenge.server.challenge.dto.ChallengeDto;
import challenge.server.challenge.dto.WildcardDto;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.entity.Wildcard;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChallengeMapper {
    ChallengeDto.Response toDto(Challenge challenge);

    WildcardDto.Response toDto(Wildcard wildcard);

    List<WildcardDto.Response> toWildcardDtos(List<Wildcard> wildcards);

    List<ChallengeDto.Response> toDtos(List<Challenge> challenges);
}
