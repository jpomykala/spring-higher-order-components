package com.jpomykala.springhoc.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.util.Arrays;
import java.util.Objects;

public class UploadRequest {

  private byte[] bytes;
  private String filePath;
  private ObjectMetadata metadata;

  public UploadRequest(byte[] bytes, String filePath, ObjectMetadata metadata) {
    this.bytes = bytes;
    this.filePath = filePath;
    this.metadata = metadata;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public void setBytes(byte[] bytes) {
    this.bytes = bytes;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public ObjectMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(ObjectMetadata metadata) {
    this.metadata = metadata;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UploadRequest)) return false;
    UploadRequest that = (UploadRequest) o;
    return Arrays.equals(bytes, that.bytes) &&
            Objects.equals(filePath, that.filePath) &&
            Objects.equals(metadata, that.metadata);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(filePath, metadata);
    result = 31 * result + Arrays.hashCode(bytes);
    return result;
  }
}
