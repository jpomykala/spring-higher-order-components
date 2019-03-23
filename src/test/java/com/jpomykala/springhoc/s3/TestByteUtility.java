package com.jpomykala.springhoc.s3;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ThreadLocalRandom;

final class TestByteUtility {
  private TestByteUtility() {
    //hidden
  }

  static byte[] generateRandomByteStream() {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    int randomSize = ThreadLocalRandom.current().nextInt(10_000, 100_000);
    for (int i = 0; i < randomSize; i++) {
      int randomByte = ThreadLocalRandom.current().nextInt();
      byteArrayOutputStream.write(randomByte);
    }

    return byteArrayOutputStream.toByteArray();
  }
}
