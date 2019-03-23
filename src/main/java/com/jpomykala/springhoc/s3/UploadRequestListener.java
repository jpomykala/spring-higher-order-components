package com.jpomykala.springhoc.s3;

import com.jpomykala.springhoc.s3.model.UploadRequest;
import org.springframework.context.event.EventListener;

public class UploadRequestListener {

  private final UploadService uploadService;

  public UploadRequestListener(UploadService uploadService) {
    this.uploadService = uploadService;
  }

  @EventListener(UploadRequest.class)
  public void onUploadRequest(UploadRequest uploadRequest){
    //TODO add async
    uploadService.upload(uploadRequest);
  }

}
