//package challenge.server.commons.firebase;
//
//import challenge.server.user.dto.UserDto;
//import challenge.server.user.entity.User;
//import challenge.server.user.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;
//
//@RequiredArgsConstructor
//@Service
//public class FCMTokenDao {
//    private final UserService userService;
//
//    public void saveToken(UserDto.LoginRequest loginRequest) {
//        User findUser = userService.findUserByUsername(loginRequest.getUsername());
//        findUser.setFcmToken();
//
//
//        tokenRedisTemplate.opsForValue()
//                .set(loginRequest.getEmail(), loginRequest.getToken());
//    }
//
//    public String getToken(String email) {
//        return tokenRedisTemplate.opsForValue().get(email);
//    }
//
//    public void deleteToken(String email) {
//        tokenRedisTemplate.delete(email);
//    }
//
//    public boolean hasKey(String email) {
//        return tokenRedisTemplate.hasKey(email);
//    }
//}
