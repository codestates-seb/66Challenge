//package challenge.server.user.service;
//
//import challenge.server.user.entity.User;
//import challenge.server.user.repository.UserRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
////@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//    @Autowired
////    @InjectMocks
//    private UserService userService;
//
//    @Autowired
////    @Mock
//    private UserRepository userRepository;
//
//    @Test
//    @DisplayName(value = "이메일 중복 여부 확인")
//    void verifyExistEmail() {
//        // given
//        User user1 = User.builder()
//                .email("user1@gmail.com")
//                .password("Abc12&defg")
//                .username("user1번")
//                .build();
//
//        User savedUser = userService.createUser(user1);
//        System.out.println(savedUser.getUserId());
//
//        String email1 = "user1@gmail.com";
//        String email2 = "mimi@gmail.com";
//
//        // when + then
//        assertTrue(userService.verifyExistEmail(email1));
//        assertFalse(userService.verifyExistEmail(email2));
//    }
//
//    @Test
//    @DisplayName(value = "회원 닉네임 중복 여부 확인")
//    void verifyExistUsername() {
//    }
//
//    // 2023.1.18(수) 오전 Postman으로 기능 테스트함
////    @Test
////    void createUser() {
////    }
//
//    // 2023.1.18(수) 낮 Postman으로 기능 테스트함
////    @Test
////    @DisplayName(value = "회원 정보 수정")
////    void updateUserPassword() {
////        // given
////        User user = User.builder()
////                .userId(1L)
////                .email("user1@gmail.com")
////                .password("Abc12&defg") // 수정 가능
////                .username("user1번") // 수정 가능
////                .build();
////
//////        given(userRepository.save(Mockito.any(User.class))).willReturn(user);
////
////        User userForPasswordUpdate = User.builder()
////                .userId(1L)
////                .password("aBc34!defG")
////                .username(null)
////                .build();
////
//////        given(userRepository.findById(Mockito.anyLong())).willReturn(Optional.of(userForPasswordUpdate));
////        System.out.println(userForPasswordUpdate.getPassword()); // aBc34!defG
////
////        // when
//////        assertThrows()
////        given(userService.findVerifiedUser(1L)).willReturn(user);
////        given(userRepository.findById(Mockito.anyLong())).willReturn(Optional.of(user));
////        User updatedUser = userService.updateUser(userForPasswordUpdate);
////
////        // then
////        assertEquals(userForPasswordUpdate.getPassword(), updatedUser.getPassword());
////    }
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