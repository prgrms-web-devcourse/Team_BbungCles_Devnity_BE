package com.devnity.devnity.setting.provider;

import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;

@Component
public class MediaProvider {

  private final ResourceLoader resourceLoader;

  public MediaProvider(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  public MockMultipartFile createMockFile(String keyName) throws IOException {
    return new MockMultipartFile(
      keyName,
      "dummy.png",
      "image/png",
      new FileInputStream(resourceLoader.getResource("classpath:dummy.png").getFile())
    );
  }

}
