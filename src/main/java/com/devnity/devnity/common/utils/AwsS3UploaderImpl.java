package com.devnity.devnity.common.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.devnity.devnity.common.error.exception.S3UploadException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class AwsS3UploaderImpl implements AwsS3Uploader{

  private final AmazonS3Client amazonS3Client;
//  private final Environment env;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  @Value("${cloud.aws.cloudfront.url}")
  private String cloudFrontUrl;

  static final String[] PERMISSION_IMG_EXTENSIONS = {"png", "jpeg", "gif", "tif", "ico", "svg", "bmp", "webp", "tiff", "jfif"};

  public String uploadFile(MultipartFile file, String dirName) {
    if (isNullFile(file)) {
      return null;
    }
    return putS3(file, dirName);
  }

  public String uploadImageFile(MultipartFile file, String dirName) {
    if (isNullFile(file)) {
      return null;
    }

    // 지원하는 이미지 확장자인지 확인
    String extension = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
    if (!isPermissionExt(extension, PERMISSION_IMG_EXTENSIONS)){
      throw new S3UploadException();
    }

    return putS3(file, dirName);
  }

// --------------------------------------------------------------------------------------------------------------

  public Boolean isNullFile(MultipartFile file) {
    if (file == null) {
      log.info("파일이 비어있습니다. null을 반환합니다.");
      return true;
    }
    return false;
  }

  public Boolean isPermissionExt(String extension, String[] permissionExtensions) {
    for (String permissionExtension : permissionExtensions) {
      if (extension.equals(permissionExtension)) {
        return true;
      }
    }
    log.info("{}은(는) 지원하지 않는 확장자입니다.", extension);
    return false;
  }

  public String putS3(MultipartFile file, String dirName) {
    String fileName = dirName + "/" + UUID.randomUUID();

    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentType(file.getContentType());
    objectMetadata.setContentLength(file.getSize());

    try (InputStream inputStream = file.getInputStream()) {
      amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
        .withCannedAcl(CannedAccessControlList.PublicRead));
      log.info("파일이 S3에 정상적으로 업로드되었습니다.");
    } catch (IOException e) {
      log.info("{}", e.getMessage());
      throw new S3UploadException();
    }

    return cloudFrontUrl + fileName;









//    // FIXME : 테스트 코드에서 overriding 으로 mocking 해주기
//    if (Arrays.asList(env.getActiveProfiles()).contains(List.of("h2", "local", "dev", "prod"))) {
//      ObjectMetadata objectMetadata = new ObjectMetadata();
//      objectMetadata.setContentType(file.getContentType());
//      objectMetadata.setContentLength(file.getSize());
//
//      try (InputStream inputStream = file.getInputStream()) {
//        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
//          .withCannedAcl(CannedAccessControlList.PublicRead));
//        log.info("파일이 S3에 정상적으로 업로드되었습니다.");
//      } catch (IOException e) {
//        log.info("{}", e.getMessage());
//        throw new S3UploadException();
//      }
//
//      return cloudFrontUrl + fileName;
//    } else {
//      // 프로필 환경변수가 test일 경우엔 dummy url을 반환
//      return "https://{CloudFront URL}/" + fileName;
//    }
  }


}
