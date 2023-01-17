package challenge.server.file.storage;

import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.file.util.MultipartUtil;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3ResourceStorage {
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * 실제 S3로 파일을 업로딩하는 메서드입니다.
     */
    public String store(String fullPath, MultipartFile multipartFile) {
        File file = new File(MultipartUtil.getLocalHomeDirectory(), fullPath);
        try {
            multipartFile.transferTo(file);     // MultipartFile을 File 객체의 형태로 변환해줍니다.
            amazonS3Client.putObject(new PutObjectRequest(bucket, fullPath, file)   // 파일이 복사되어 임시파일과 같이 로컬에 저장이 됩니다.
                    .withCannedAcl(CannedAccessControlList.PublicRead));    // 누구나 파일에 접근이 가능합니다.
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.FAILED_TO_UPLOAD_FILE);
        } finally {
            if (file.exists()) removeNewFile(file);   // 로컬에 있는 파일을 지워줍니다.
        }
        return amazonS3Client.getUrl(bucket, fullPath).toString();  // S3에 업로드된 파일 URL 반환
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("The file has been deleted.");
        } else {
            log.info("Failed to delete file.");
        }
    }

}
