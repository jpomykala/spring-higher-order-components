package me.jpomykala.starters.springhoc.s3;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.google.common.hash.Hashing;
import me.jpomykala.starters.springhoc.s3.model.UploadRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class UploadService {

  private final SpringHocS3Properties properties;
  private final AmazonS3 amazonS3;

  public UploadService(SpringHocS3Properties properties, AmazonS3 amazonS3) {
    this.properties = properties;
    this.amazonS3 = amazonS3;
  }

  public void upload(@NotNull UploadRequest uploadRequest) {
    byte[] requestBytes = uploadRequest.getBytes();
    String filePath = uploadRequest.getFilePath();
    ObjectMetadata metadata = uploadRequest.getMetadata();
    upload(requestBytes, filePath, metadata);
  }

  public void upload(@NotNull MultipartFile file) throws IOException {
    upload(file, "");
  }

  public void upload(@NotNull MultipartFile file, @NotNull String path) throws IOException {
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
    upload(bytes, path + "/" + originalFilename, objectMetadata);
  }

  public String upload(byte[] bytes) {
    String generatedFilePath = Hashing.sha256().toString();
    upload(bytes, generatedFilePath);
    return generatedFilePath;
  }

  public void upload(byte[] bytes, String fileKey) {
    upload(bytes, fileKey, new ObjectMetadata());
  }

  public void upload(byte[] bytes, String fileKey, ObjectMetadata metadata) {
    int length = bytes.length;
    if (length == 0) {
      throw new IllegalArgumentException("File has 0 bytes");
    }
    InputStream inputStream = new ByteArrayInputStream(bytes);
    metadata.setContentLength(length);
    String bucketName = properties.getBucketName();
    amazonS3.putObject(bucketName, fileKey, inputStream, metadata);
  }
}

