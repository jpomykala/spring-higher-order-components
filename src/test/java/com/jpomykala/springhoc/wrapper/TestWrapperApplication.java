package com.jpomykala.springhoc.wrapper;

import org.assertj.core.util.Lists;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication(scanBasePackages = {"com.jpomykala.springhoc.wrapper"})
@RestController
public class TestWrapperApplication extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(TestWrapperApplication.class, args);
  }

  @GetMapping("/echo")
  public MyPojo echo() {
    return new MyPojo()
            .setFirstName("Jakub")
            .setLastName("Pomykała");
  }

  @GetMapping("/paged-echo")
  public Page<MyPojo> pageEcho() {
    MyPojo jakub = new MyPojo()
            .setFirstName("Jakub")
            .setLastName("Pomykała");

    MyPojo nieMaJajek = new MyPojo()
            .setFirstName("Michał")
            .setLastName("Białek");

    List<MyPojo> pojoList = Lists.newArrayList(jakub, nieMaJajek);
    int totalElements = pojoList.size();
    return new PageImpl<>(pojoList, Pageable.unpaged(), totalElements);
  }

  @GetMapping("/response-entity-echo")
  public ResponseEntity<MyPojo> responseEntityEcho() {
    MyPojo jakub = new MyPojo()
            .setFirstName("Jakub")
            .setLastName("Pomykała");
    return ResponseEntity.ok(jakub);
  }


  public class MyPojo {
    private String firstName;
    private String lastName;

    public String getFirstName() {
      return firstName;
    }

    public MyPojo setFirstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public String getLastName() {
      return lastName;
    }

    public MyPojo setLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }
  }
}
