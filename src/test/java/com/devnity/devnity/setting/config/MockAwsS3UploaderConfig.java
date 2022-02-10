package com.devnity.devnity.setting.config;

import com.devnity.devnity.domain.s3.AwsS3Uploader;
import java.util.UUID;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartFile;

@TestConfiguration
public class MockAwsS3UploaderConfig {

  @Bean
  public AwsS3Uploader awsS3Uploader( ){
    return new AwsS3Uploader() {
      @Override
      public String uploadFile(MultipartFile file, String dirName) {
        return "https://{CloudFront_URL}/" + dirName + "/" + UUID.randomUUID();
      }
      @Override
      public String uploadImageFile(MultipartFile file, String dirName) {
        return "https://{CloudFront_URL}/" + dirName + "/" + UUID.randomUUID();
      }
    };
  }

}
