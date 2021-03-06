package com.devnity.devnity.web.media.service;

import com.devnity.devnity.common.s3.AwsS3Uploader;
import com.devnity.devnity.web.media.dto.ImageUrlResponse;
import com.devnity.devnity.web.media.dto.MediaUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class MediaService {

  private final AwsS3Uploader awsS3Uploader;

  public MediaUrlResponse uploadMedia(MultipartFile file, String dirName){
    return MediaUrlResponse.of(awsS3Uploader.uploadFile(file, dirName));
  }

  public ImageUrlResponse uploadImage(MultipartFile imageFile, String dirName){
    return ImageUrlResponse.of(awsS3Uploader.uploadImageFile(imageFile, dirName));
  }

}
