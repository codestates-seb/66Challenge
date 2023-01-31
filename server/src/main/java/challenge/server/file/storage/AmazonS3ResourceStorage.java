package challenge.server.file.storage;

import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.file.util.MultipartUtil;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.System.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3ResourceStorage {
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    /**
     * 실제 S3로 파일을 업로딩하는 메서드입니다.
     */
    public String store(String fullPath, MultipartFile multipartFile) {
        File file = new File(MultipartUtil.getLocalHomeDirectory(), fullPath);

        try {
            multipartFile.transferTo(file);     // MultipartFile을 File 객체의 형태로 변환해줍니다.
//            createThumbnail(fullPath, file);

            amazonS3Client.putObject(new PutObjectRequest(bucket, fullPath, file)   // 파일이 복사되어 임시파일과 같이 로컬에 저장이 됩니다.
                    .withCannedAcl(CannedAccessControlList.PublicRead));    // 누구나 파일에 접근이 가능합니다.
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessLogicException(ExceptionCode.FAILED_TO_UPLOAD_FILE);
        } finally {
            if (file.exists()) removeNewFile(file);   // 로컬에 있는 파일을 지워줍니다.
        }
//        out.println(amazonS3Client.getObject(bucket, fullPath).toString());   // timeout에 원인 getObject 사용하면 close해야됩니다.
        return amazonS3Client.getUrl(bucket, fullPath).toString().replaceFirst("s", "");  // S3에 업로드된 파일 URL 반환
    }

//    private void createThumbnail(String fullPath, File file) {
//        double ratio = 2;   // 이미지 축소 비율
//
//        int idx = fullPath.lastIndexOf(".");
//        String ext = fullPath.substring(idx + 1);// 파일 확장자
//
//        try {
//            BufferedImage oimage = ImageIO.read(file);  // original image
//
//            int tWidth = (int) (oimage.getWidth() / ratio);
//            int tHeight = (int) (oimage.getHeight() / ratio);
//
//            BufferedImage thumbnail = new BufferedImage(tWidth, tHeight, BufferedImage.TYPE_3BYTE_BGR); // 썸네일
//
//            Graphics2D graphic = thumbnail.createGraphics();
//            Image image = oimage.getScaledInstance(tWidth, tHeight, Image.SCALE_SMOOTH);
//            graphic.drawImage(image, 0, 0, tWidth, tHeight, null);
//            graphic.dispose();      // 리소스를 모두 해제
//
//            ImageIO.write(thumbnail, ext, file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) log.info("The file has been deleted.");
        else {
            log.info("Failed to delete file.");
        }
    }

    public void delete(String fileUrl) {
        try {
            String key = fileUrl.substring(64);
            try {
                amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
            } catch (AmazonServiceException e) {
                log.error(e.getErrorMessage());
                exit(1);
            }
            log.info(String.format("[%s] deletion complete", key));
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.FAILED_TO_DELETE_FILE);
        }
    }
}
