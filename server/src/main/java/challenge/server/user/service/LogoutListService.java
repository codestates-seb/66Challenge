package challenge.server.user.service;

import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.user.entity.LogoutList;
import challenge.server.user.repository.LogoutListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutListService {
    private final LogoutListRepository logoutListRepository;

    public LogoutList findByAccessToken(String accessToken) {
        return logoutListRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }
}
