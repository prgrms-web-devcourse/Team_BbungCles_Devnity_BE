package com.devnity.devnity.domain.s3;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Uploader {
  String uploadFile(MultipartFile file, String dirName);
  String uploadImageFile(MultipartFile file, String dirName);
}
