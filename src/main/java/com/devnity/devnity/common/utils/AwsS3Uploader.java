package com.devnity.devnity.common.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class AwsS3Uploader {

  private final AmazonS3Client amazonS3Client;
  private final Environment env;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  @Value("${cloud.aws.cloudfront.url}")
  private String cloudFrontUrl;

  public String upload(MultipartFile file, String dirName) {
    if (file == null) {
      log.info("파일이 비어있습니다. null을 반환합니다.");
      return null;
    }
    String fileName = dirName + "/" + UUID.randomUUID();

    if (Arrays.asList(env.getActiveProfiles()).contains(List.of("h2", "local", "dev", "prod"))) {
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentType(file.getContentType());
      objectMetadata.setContentLength(file.getSize());

      try (InputStream inputStream = file.getInputStream()) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
          .withCannedAcl(CannedAccessControlList.PublicRead));
        log.info("이미지가 S3에 정상적으로 업로드되었습니다.");
      } catch (IOException e) {
        log.info("{}", e.getMessage());
      }
      return cloudFrontUrl + fileName;
    } else {
      // 프로필 환경변수가 test일 경우엔 dummy url을 반환
      return "https://{CloudFront URL}/" + fileName;
    }
  }

}
