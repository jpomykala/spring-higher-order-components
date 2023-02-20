package com.jpomykala.springhoc.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

@RunWith(MockitoJUnitRunner.class)
public class UploadServiceTest {

  @InjectMocks
  private UploadService uploadService;

  @Mock
  private AmazonS3Properties amazonS3Properties;

  @Mock
  private AmazonS3 amazonS3;

  @Test
  public void shouldUploadWhenUploadRequest() throws Exception {
    //given
    byte[] bytes = TestByteUtility.generateRandomByteStream();
    ObjectMetadata objectMetadata = new ObjectMetadata();
    UploadRequest uploadRequest = new UploadRequest(bytes, "file/doc.txt", objectMetadata);

    Mockito.when(amazonS3.putObject(Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any(ObjectMetadata.class))).thenReturn(new PutObjectResult());
    Mockito.when(amazonS3Properties.getBucketName()).thenReturn("my-test-bucket");

    //when
    String uploadUrl = uploadService.upload(uploadRequest);

    //then
    Assertions.assertThat(uploadUrl)
            .isNotEmpty()
            .containsIgnoringCase("my-test-bucket")
            .startsWith("https://");
  }

  @Test
  public void shouldUploadWhenMultipartFile() throws Exception {
    //given
    byte[] bytes = TestByteUtility.generateRandomByteStream();
    MockMultipartFile mockMultipartFile = new MockMultipartFile("file.txt", bytes);

    Mockito.when(amazonS3.putObject(Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any(ObjectMetadata.class))).thenReturn(new PutObjectResult());
    Mockito.when(amazonS3Properties.getBucketName()).thenReturn("my-test-bucket");

    //when
    String uploadUrl = uploadService.upload(mockMultipartFile);

    //then
    Assertions.assertThat(uploadUrl)
            .isNotEmpty()
            .containsIgnoringCase("my-test-bucket")
            .startsWith("https://");
  }

  @Test
  public void shouldUploadWhenBytes() throws Exception {
    //given
    byte[] bytes = TestByteUtility.generateRandomByteStream();

    Mockito.when(amazonS3.putObject(Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any(ObjectMetadata.class))).thenReturn(new PutObjectResult());
    Mockito.when(amazonS3Properties.getBucketName()).thenReturn("my-test-bucket");

    //when
    String uploadUrl = uploadService.upload(bytes);

    //then
    Assertions.assertThat(uploadUrl)
            .isNotEmpty()
            .containsIgnoringCase("my-test-bucket")
            .startsWith("https://");
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldTrowWhen0Bytes() throws Exception {
    //given
    byte[] bytes = TestByteUtility.generateRandomByteStream(0);

    //when
    uploadService.upload(bytes);

    //then
  }
}
