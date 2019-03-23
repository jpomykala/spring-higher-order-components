package com.jpomykala.springhoc.s3;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ThreadLocalRandom;

final class TestByteUtility {
  private TestByteUtility() {
    //hidden
  }

  static byte[] generateRandomByteStream(int size) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    for (int i = 0; i < size; i++) {
      int randomByte = ThreadLocalRandom.current().nextInt();
      byteArrayOutputStream.write(randomByte);
    }

    return byteArrayOutputStream.toByteArray();
  }

  static byte[] generateRandomByteStream() {
    int randomSize = ThreadLocalRandom.current().nextInt(10_000, 100_000);
    return generateRandomByteStream(randomSize);
  }
}
