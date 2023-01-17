package challenge.server.file.dto;

import challenge.server.file.util.MultipartUtil;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 사용 여부 확정 후 삭제 결정
 */
@Getter
@Builder
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FileDto {
    private String id;
    private String name;
    private String format;
    private String path;
    private long bytes;

    public static FileDto multipartOf(MultipartFile multipartFile) {
        final String fileId = MultipartUtil.createFileId();
        final String format = MultipartUtil.getFormat(multipartFile.getContentType());

        return FileDto.builder()
                .id(fileId)     // 36자리 UUID형태로 생성
                .name(multipartFile.getOriginalFilename())  // 파일 업로드 시점의 파일명
                .format(format)     // 해당 파일의 확장자에 대한 값 ex) .jpg .png
                .path(MultipartUtil.createPath(multipartFile))     // 파일의 실제 물리적 경로값
                .bytes(multipartFile.getSize())
                .build();
    }
}
