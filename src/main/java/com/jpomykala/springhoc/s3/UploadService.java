package com.jpomykala.springhoc.s3;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public class UploadService {

  private final AmazonS3Properties properties;
  private final AmazonS3 amazonS3;

  public UploadService(
          AmazonS3Properties properties,
          AmazonS3 amazonS3) {
    this.properties = properties;
    this.amazonS3 = amazonS3;
  }

  public String upload(@NotNull UploadRequest uploadRequest) {
    byte[] requestBytes = uploadRequest.getBytes();
    String filePath = uploadRequest.getFilePath();
    ObjectMetadata metadata = uploadRequest.getMetadata();
    return upload(requestBytes, filePath, metadata);
  }

  public String upload(@NotNull MultipartFile file) throws IOException {
    return upload(file, "");
  }

  public String upload(@NotNull MultipartFile file, @NotNull String path) throws IOException {
    Optional<MultipartFile> multipartFile = Optional.of(file);
    String originalFilename = multipartFile
            .map(MultipartFile::getOriginalFilename)
            .orElse("unknown_name");


    String contentType = multipartFile
            .map(MultipartFile::getContentType)
            .orElse("");

    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentType(contentType);

    byte[] bytes = file.getBytes();
    return upload(bytes, path + "/" + originalFilename, objectMetadata);
  }

  public String upload(byte[] bytes) {
    String generatedFilePath = UUID.randomUUID().toString().replace("-", "").toLowerCase();
    upload(bytes, generatedFilePath);
    return getDownloadUrl(generatedFilePath);
  }

  public String upload(byte[] bytes, String fileKey) {
    return upload(bytes, fileKey, new ObjectMetadata());
  }

  public String upload(byte[] bytes, String fileKey, ObjectMetadata metadata) {
    int length = bytes.length;
    if (length == 0) {
      throw new IllegalArgumentException("File has 0 bytes");
    }
    InputStream inputStream = new ByteArrayInputStream(bytes);
    metadata.setContentLength(length);
    String bucketName = properties.getBucketName();
    amazonS3.putObject(bucketName, fileKey, inputStream, metadata);
    return getDownloadUrl(fileKey);
  }

  public String getDownloadUrl(String fileKey) {
    return "https://" + properties.getBucketName() + "/" + fileKey;
  }
}

