package com.jpomykala.springhoc.s3.model;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.jpomykala.springhoc.s3.TestByteUtility;
import com.jpomykala.springhoc.s3.UploadRequest;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class UploadRequestTest {

  @Test
  public void shouldEqualsWhenSameArguments() throws Exception {
    //given
    byte[] bytes = TestByteUtility.generateRandomByteStream();
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType("pdf");

    UploadRequest uploadRequest1 = new UploadRequest(bytes, "path", metadata);
    UploadRequest uploadRequest2 = new UploadRequest(bytes, "path", metadata);

    //when
    boolean result = uploadRequest1.equals(uploadRequest2);

    //then
    Assertions.assertThat(result).isTrue();
  }

  @Test
  public void shouldNotEqualsWhenDifferentBytes() throws Exception {
    //given
    byte[] bytes1 = TestByteUtility.generateRandomByteStream(500);
    byte[] bytes2 = TestByteUtility.generateRandomByteStream(600);
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType("pdf");

    UploadRequest uploadRequest1 = new UploadRequest(bytes1, "path", metadata);
    UploadRequest uploadRequest2 = new UploadRequest(bytes2, "path", metadata);

    //when
    boolean result = uploadRequest1.equals(uploadRequest2);

    //then
    Assertions.assertThat(result).isFalse();
  }

  @Test
  public void shouldHashCode() throws Exception {
    //given
    byte[] bytes = TestByteUtility.generateRandomByteStream();
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType("pdf");

    UploadRequest uploadRequest1 = new UploadRequest(bytes, "path", metadata);
    UploadRequest uploadRequest2 = new UploadRequest(bytes, "path", metadata);

    //when
    int hashCode1 = uploadRequest1.hashCode();
    int hashCode2 = uploadRequest2.hashCode();

    //then
    Assertions.assertThat(hashCode1).isEqualTo(hashCode2);
  }
}
