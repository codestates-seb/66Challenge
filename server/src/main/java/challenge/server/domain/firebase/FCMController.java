package challenge.server.domain.firebase;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FCMController {
    private final FCMService fcmService;

//    @PostMapping("/fcm/{user-id}")
//    public ResponseEntity activeUser(@PathVariable("user-id") Long userId) {
//        fcmService.sendItemActiveNotice(userId);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
