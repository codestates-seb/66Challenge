package challenge.server.firebase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public void save(Notice notice) {
        noticeRepository.save(notice);
    }
}
