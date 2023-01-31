package challenge.server.file.service;

import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.file.storage.AmazonS3ResourceStorage;
import challenge.server.file.util.MultipartUtil;
import com.amazonaws.SdkBaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUploadService {

    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    /**
     * 실제 물리적인 File은 업로드 처리를 하고 imageUrl을 반환한다.
     */
    public String save(MultipartFile multipartFile) {
        verfiedExenstion(multipartFile);
        String fullPath = MultipartUtil.createPath(multipartFile);
        return amazonS3ResourceStorage.store(fullPath, multipartFile);
    }

    public void delete(String fileUrl) throws SdkBaseException {
        amazonS3ResourceStorage.delete(fileUrl);
    }

    public void verfiedExenstion(MultipartFile multipartFile) throws BusinessLogicException {
        String contentType = multipartFile.getContentType();
//        System.out.println("contentType = " + contentType); // multipart/form-data, image/png 등 포스트맨에서 설정한 게 나옴

        // 확장자가 jpeg, png인 파일들만 받아서 처리
        if (ObjectUtils.isEmpty(contentType) || (!contentType.contains("image/jpeg") && !contentType.contains("image/png")))
            throw new BusinessLogicException(ExceptionCode.EXTENSION_IS_NOT_VAILD);
    }
}
