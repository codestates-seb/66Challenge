package challenge.server.challenge.mapper;

import challenge.server.challenge.dto.ChallengeDto;
import challenge.server.challenge.dto.WildcardDto;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.entity.Wildcard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring") // uses = {AuthMapper.class})
public interface ChallengeMapper {

    @Mapping(expression = "java(getAuthIds(challenge))", target = "authIds")
    @Mapping(expression = "java(countUsedWildcards(challenge))", target = "usedWildcard")
    @Mapping(source = "challenge.habit.title", target = "habitTitle")
    @Mapping(source = "challenge.habit.subTitle", target = "habitSubTitle")
    @Mapping(source = "challenge.user.username", target = "challenger")
    ChallengeDto.Response toDto(Challenge challenge);

    WildcardDto.Response toDto(Wildcard wildcard);

    List<WildcardDto.Response> toWildcardDtos(List<Wildcard> wildcards);

    List<ChallengeDto.Response> toDtos(List<Challenge> challenges);

    default Integer countUsedWildcards(Challenge challenge) {
        if(challenge.getWildcards() == null) return 0;
        return challenge.getWildcards().size();
    }

    default List<Long> getAuthIds(Challenge challenge) {
        if(challenge.getAuths() == null) return new ArrayList<>();
        return challenge.getAuths().stream().map(
                 auth -> auth.getAuthId()
                ).collect(Collectors.toList());
    }
}