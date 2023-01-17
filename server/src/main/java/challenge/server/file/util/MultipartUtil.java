package challenge.server.file.util;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public final class MultipartUtil {
    private static final String BASE_DIR = "images";

    /**
     *  로컬에서의 사용자 홈 디렉토리 경로를 반환
     *  OS X의 경우 파일 시스템 쓰기 권한 문제로 인해 필히 사용자 홈 디렉토리 또는 쓰기가 가능한 경로로 설정해주어야 합니다.
     */
    public static String getLocalHomeDirectory() {
        return System.getProperty("user.home");
    }

    /**
     * 새로운 파일 고유 ID를 생성합니다.
     * @return 36자리의 UUID
     */
    public static String createFileId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Multipart의 ContentType 값에서 / 이후 확장자만 잘라냅니다.
     * @return ex) png
     * @Param contentType ex) image/png
     */
    public static String getFormat(String contentType) {
        if (StringUtils.hasText(contentType)) {
            return contentType.substring(contentType.lastIndexOf('/') + 1);
        }
        return null;
    }

    /**
     * 파일의 전체 경로를 생성합니다.
     * fileId : 생성된 파일 고유 ID
     * format : 확장자
     */
    public static String createPath(MultipartFile multipartFile) {
        final String fileId = MultipartUtil.createFileId();
        final String format = MultipartUtil.getFormat(multipartFile.getContentType());
        return String.format("%s/%s.%s", BASE_DIR, fileId, format);
    }
}
