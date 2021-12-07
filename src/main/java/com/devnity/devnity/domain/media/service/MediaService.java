package com.devnity.devnity.domain.media.service;

import com.devnity.devnity.common.utils.AwsS3UploaderTemp;
import com.devnity.devnity.domain.media.dto.MediaUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class MediaService {

  private final AwsS3UploaderTemp awsS3Uploader;

  public MediaUrlResponse convertImage(MultipartFile file){
    return MediaUrlResponse.of(awsS3Uploader.upload(file, "media"));
  }

}
