package com.devnity.devnity.dummy;

import com.devnity.devnity.common.utils.AwsS3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Transactional
@RequiredArgsConstructor
@Service
public class TestService {

  private final AwsS3Uploader awsS3Uploader;

  private final TestRepository testRepository;


  public Map<String, String> insertDummy() {
    TestEntity dummy = TestEntity.builder().str("dummy").build();
    testRepository.save(dummy);
    return Map.of("result", "success - insert dummy");
  }

//  public Map<String, String> insertImage(DummyImageRequest request) {
//    String resultUrl = awsS3Uploader.upload(request.getImageBase64(), "test");
//    return Map.of("resultUrl", resultUrl);
//  }

  public Map<String, String> insertImageTemp(MultipartFile imageFile, JsonRequest request){
    System.out.println(request.getNum());
    System.out.println(request.getText());

    String resultUrl = awsS3Uploader.upload(imageFile, "test");
    if(resultUrl == null){
      resultUrl = "널이에용";
    }
    return Map.of("resultUrl", resultUrl);
  }
}
