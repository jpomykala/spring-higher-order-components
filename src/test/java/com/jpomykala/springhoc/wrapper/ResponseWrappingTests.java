package com.jpomykala.springhoc.wrapper;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ResponseWrappingTests {

  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders
            .standaloneSetup(ExampleController.class)
            .setControllerAdvice(ResponseWrapper.class)
            .build();
  }

  @Test
  public void testWrapperWithSamplePojo() throws Exception {
    this.mockMvc
            .perform(get("/examples/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.msg").value("OK"))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.data.name").value("Example"))
            .andExpect(jsonPath("$.data.age").value(100))
            .andExpect(jsonPath("$.data.pageDetails").doesNotExist())
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void testWrapperWithPageOfSamplePojos() throws Exception {
    this.mockMvc
            .perform(get("/examples"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.msg").value("OK"))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.data[0].name").value("Example"))
            .andExpect(jsonPath("$.data[0].age").value(100))
            .andExpect(jsonPath("$.pageDetails.totalElements").value(1))
            .andExpect(jsonPath("$.pageDetails.currentPage").value(0))
            .andExpect(jsonPath("$.pageDetails.totalPages").value(1))
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void testWrapperWithNotWrappedResponseEntity() throws Exception {
    this.mockMvc
            .perform(get("/not-wrapped"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("Jakub"))
            .andExpect(jsonPath("$.age").value(24))
            .andDo(MockMvcResultHandlers.print());
  }


  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @Import({ServletWebServerFactoryAutoConfiguration.class,
          DispatcherServletAutoConfiguration.class, WebMvcAutoConfiguration.class,
          HttpMessageConvertersAutoConfiguration.class,
          PropertyPlaceholderAutoConfiguration.class})
  protected @interface MinimalWebConfiguration {
  }

  @Configuration
  @MinimalWebConfiguration
  protected static class TestWebApplicationConfiguration {

  }

  @RestController
  protected static class ExampleController {

    private final SamplePojo example = new SamplePojo("Example", 100);
    private final List<SamplePojo> singleElementList = Lists.newArrayList(example);

    @GetMapping("/examples/1")
    public SamplePojo examples() {
      return singleElementList.get(0);
    }

    @GetMapping("/examples")
    public Page<SamplePojo> examplePojo() {
      PageRequest pageRequest = PageRequest.of(0, 1, Sort.Direction.ASC, "name");
      return new PageImpl(singleElementList, pageRequest, 1L);
    }


    @GetMapping("/not-wrapped")
    public ResponseEntity<SamplePojo> responseEntityEcho() {
      SamplePojo jakub = new SamplePojo();
      jakub.setName("Jakub");
      jakub.setAge(24);
      return ResponseEntity.ok(jakub);
    }
  }

  protected static class SamplePojo {
    private String name;
    private int age;

    public SamplePojo() {
    }

    public SamplePojo(String name, int age) {
      this.name = name;
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }
  }
}
