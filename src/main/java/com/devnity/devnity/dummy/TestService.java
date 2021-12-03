package com.devnity.devnity.dummy;

import com.devnity.devnity.common.utils.AwsS3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TestService {

  private final AwsS3Uploader awsS3Uploader;
  private final TestRepository testRepository;

  @Transactional
  public Map<String, String> insertDummy() {
    TestEntity dummy = TestEntity.builder().str("dummy").build();
    testRepository.save(dummy);
    return Map.of("result", "success - insert dummy");
  }

  @Transactional
  public Map<String, String> insertImage(ImageRequest request) {
    String resultUrl = awsS3Uploader.upload(request.getImageBase64(), "test");
    return Map.of("resultUrl", resultUrl);
  }
}
