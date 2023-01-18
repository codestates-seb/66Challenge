//package challenge.server.user.service;
//
//import challenge.server.user.dto.UserDto;
//import challenge.server.user.entity.User;
//import challenge.server.user.repository.UserRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//    @InjectMocks
//    private UserService userService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Test
//    void verifyExistEmail() {
//    }
//
//    @Test
//    void verifyExistUsername() {
//    }
//
//    @Test
//    void createUser() {
//    }
//
//    @Test
//    @DisplayName(value = "회원 정보 수정")
//    void updateUserPassword() {
//        // given
//        User user = User.builder()
//                .userId(1L)
//                .email("user1@gmail.com")
//                .password("Abc12&defg") // 수정 가능
//                .username("user1번") // 수정 가능
//                .build();
//
//
////        given(userRepository.save(Mockito.any(User.class))).willReturn(user);
//
//        User userForPasswordUpdate = User.builder()
//                .userId(1L)
//                .password("aBc34!defG")
//                .username(null)
//                .build();
//
////        given(userRepository.findById(Mockito.anyLong())).willReturn(Optional.of(userForPasswordUpdate));
//        System.out.println(userForPasswordUpdate.getPassword()); // aBc34!defG
//
//        // when
////        assertThrows()
//        given(userService.findVerifiedUser(1L)).willReturn(user);
//        given(userRepository.findById(Mockito.anyLong())).willReturn(Optional.of(user));
//        User updatedUser = userService.updateUser(userForPasswordUpdate);
//
//        // then
//        assertEquals(userForPasswordUpdate.getPassword(), updatedUser.getPassword());
//    }
//
//    @Test
//    void verifyExistUser() {
//    }
//
//    @Test
//    void findUser() {
//    }
//
//    @Test
//    void findVerifiedUser() {
//    }
//}