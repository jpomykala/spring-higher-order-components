![spring-hoc](https://i.imgur.com/8LA1Xcp.png)

[![Build Status](https://travis-ci.org/jpomykala/spring-higher-order-components.svg?branch=master)](https://travis-ci.org/jpomykala/spring-higher-order-components)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.jpomykala/spring-higher-order-components/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.jpomykala/spring-higher-order-components)
[![codecov](https://codecov.io/gh/jpomykala/spring-higher-order-components/branch/master/graph/badge.svg)](https://codecov.io/gh/jpomykala/spring-higher-order-components)
[![Maintainability](https://api.codeclimate.com/v1/badges/4fc54f7f19a610dc5d42/maintainability)](https://codeclimate.com/github/jpomykala/spring-higher-order-components/maintainability)

Boilerplate components for Spring Boot. 
- [x] [E-mail sending with step builder](#enableemailsending-annotation)
- [x] [Request logging](#enablerequestlogging-annotation)
- [x] [Files uploading to Amazon S3](#enablefileuploading-annotation)
- [x] [Response wrapping](#enableresponsewrapping-annotation) (works with Swagger / SpringFox!)
- [x] [Easy to use CORS filter](#enablecors-annotation)

## Installation
```xml
<dependency>
  <groupId>com.jpomykala</groupId>
  <artifactId>spring-higher-order-components</artifactId>
  <version>1.0.11</version>
</dependency>
```

[Check version in maven repository](https://mvnrepository.com/artifact/com.jpomykala/spring-higher-order-components)

## Motivation

- Write inline code
- Duplicate code a few times in different spots
- Extract duplicate code into methods
- Use your abstractions for a while
- See how that code interacts with other code
- Extract common functionality into internal library
- Use internal library for extended periods of time
- Really understand how all of the pieces come together
- Create external open source library (we are here now)

source: [https://nickjanetakis.com](https://nickjanetakis.com/blog/microservices-are-something-you-grow-into-not-begin-with)

*** 

## `@EnableEmailSending` annotation

This component gives you simple API to send emails using Amazon SES service. Spring HOC will automatically create for you Amazon SES component if bean doesn't exist.

### Configuration

- Provide **verified** sender email address ``spring-hoc.mail.sender-email-address``
- Provide AWS credentials ``spring-hoc.aws.access-key``, ``spring-hoc.aws.secret-key``, ``spring-hoc.aws.region``

#### Example `application.yml` configuration for e-mail sending

```yml
spring-hoc:
  aws:
    access-key: xxxxxxxx
    secret-key: xxxxxxxx
    region: eu-west-1
  mail:
    sender-email-address: "no-reply@mydomain.com"    
```
This properties are **required**.

#### Tip
You can put `My Company Name <no-reply@mydomain.com>` in `sender-email-address` property to show "My Company Name" in e-mail apps instead plain e-mail.
Reference: https://docs.aws.amazon.com/ses/latest/DeveloperGuide/send-email-concepts-email-format.html

### How to send e-mail?

Use ``EmailRequest`` step builder to create request.

```java
EmailRequest.builder()
            .to("jpomykala@example.com")
            .subject("Hey, I just met you and this is crazy")
            .body("But here's my number, so call me maybe")
            .build();
```

Now it's time to send email. You have 2 options here.
- ``@Autowire MailService`` and invoke ``sendEmail(EmailRequest)``.
- Publish ``EmailRequest`` using ``ApplicationEventPublisher``

That's all!

### Example application with sending email (SES)

```java
@SpringBootApplication
@EnableEmailSending
public class MySpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(MySpringBootApplication.class, args);
  }

  // Send e-mail by event publishing, Spring HOC will listen to EmailRequest objects 
  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @GetMapping("/send-email-by-event-publishing")
  public void sendEmailByEventPublishing(){
    EmailRequest emailRequest = EmailRequest.builder()
            .to("jakub.pomykala@gmail.com")
            .subject("Hey, I just met you and this is crazy [event publishing]")
            .body("But here's my number, so call me maybe")
            .build();

    eventPublisher.publishEvent(emailRequest);
  }
  
  // Send e-mail by mail service provided by Spring HOC and @EnableEmailSending annotation
  @Autowired
  private MailService mailService;

  @GetMapping("/send-email-by-mail-service")
  public void sendEmailByMailService(){
    EmailRequest emailRequest = EmailRequest.builder()
            .to("jakub.pomykala@gmail.com")
            .subject("Hey, I just met you and this is crazy [mail service]")
            .body("But here's my number, so call me maybe")
            .build();

    mailService.send(emailRequest);
  }
}
```

*** 

## `@EnableRequestLogging` annotation

Adds logging requests, populate MDC with:
- user (IP address by default)
- requestId (UUID by default).

### Example application with request logging

```java
@SpringBootApplication
@EnableRequestLogging
public class MySpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(MySpringBootApplication.class, args);
  }

  @Autowired
  private MyUserService userService;

  // [OPTIONAL] customize configuration
  @Bean
  public LoggingFilter loggingFilter(LoggingFilterFactory loggingFilterFactory) {
    return loggingFilterFactory
            .withPrincipalProvider(new PrincipalProvider() {
              @Override
              public String getPrincipal(HttpServletRequest request) {
                return userService.findUserName(request);
              }
            })
            .createFilter();
  }
}

```


### Customization of request logging
```java
@Bean
public LoggingFilter loggingFilter(LoggingFilterFactory loggingFilterFactory){
  return loggingFilterFactory
          .withPrincipalProvider() // [optional] PrincipalProvider implementation 
          .withRequestIdProvider() // [optional] RequestIdProvider implementation
          .withCustomMdc("user", "[u:%s][rid:%s]") // [optional] MDC key, String.format()
          .createFilter();
}
```

*** 

## `@EnableFileUploading` annotation

This annotation autoconfigures Amazon S3 component if bean doesn't exit.

``@Autowire UploadService`` gives you ability to upload files using overloaded methods:
- ``void upload(@NotNull UploadRequest uploadRequest)``
- ``void upload(@NotNull MultipartFile file)``
- ``void upload(@NotNull MultipartFile file, @NotNull String path)``
- ``void upload(byte[] bytes, String fileKey)``
- ``void upload(byte[] bytes, String fileKey, ObjectMetadata metadata)``
- ``String upload(byte[] bytes)`` // path is autogenerated (sha256 hash)

### Example ``application.yml`` configuration for file uploading

```
spring-hoc:
  aws:
    access-key: xxxxxxxx
    secret-key: xxxxxxxx
    region: eu-west-1
  s3:
    bucket-name: my-bucket
```
This properties are **required.***

### Example application with files uploading

```java
@SpringBootApplication
@EnableFileUploading
public class MySpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(MySpringBootApplication.class, args);
  }

  @Autowired
  private UploadService uploadService;

  @GetMapping("/upload-file")
  public String uploadFile(@RequestBody MultipartFile multipartFile) throws IOException {
    String s3DownloadUrl = uploadService.upload(multipartFile);
    return s3DownloadUrl;
  }
}
```

*** 

## `@EnableResponseWrapping` annotation

Every `@RestController` output will be wrapped into `RestResponse<T>` object for JSON it will look like as follows:

```
{
  msg: "OK"
  status: 200
  data: <your data>
  pageDetails: <page details if you return Page from controller>
}
```

`RestResponse` static contructors:
  
  - `RestResponse ok(Object body)`
  - `RestResponse ok(Object body, PageDetails pageDetails)`
  - `RestResponse empty(String message, HttpStatus status)`
  - `RestResponse of(String message, HttpStatus status, Object data)`
  - `RestResponse of(String message, HttpStatus status, Object data, PageDetails pageDetails)`
  
Every output will be wrapped into `RestResponse` [see this issue](https://github.com/jpomykala/spring-higher-order-components/issues/4)

Response wrapping can be disabled for specific endpoinds by using `@DisableWrapping` annotation on method.

### Example application with response wrapping

```java
@SpringBootApplication
@EnableResponseWrapping
public class MySpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(MySpringBootApplication.class, args);
  }

  @GetMapping("/wrap-pojo")
  public MyPojo wrapResponse() {
    MySpringBootApplication.MyPojo myPojo = new MyPojo("Jakub", "Pomykala");
    return myPojo;
  }
  
  @Autowired
  private MyPojoRepository myPojoRepository;

  @GetMapping("/wrap-pojo-page")
  public Page<MyPojo> wrapPageResponse() {
    Page<MyPojo> myPojos = myPojoRepository.findAll();
    return myPojos;
  }
  
  public class MyPojo {
    private String firstName;
    private String lastName;
    // getters and setters
  }
}
```

*** 

## `@EnableCORS` annotation

This annotation adds filter which handles CORS requests.

### Example `application.yml` configuration for CORS

```yml
spring-hoc:
  cors:
    allow-credentials: true
    allowed-origins:
      - "https://my-frontend-application.com"
      - "https://jpomykala.com"
    allowed-methods:
      - GET
      - POST
      - PATCH
      - DELETE
```
This properties are **optional.**

By default CORS will accept all origins, all HTTP methods and all popular headers.

### Example application with CORS filter

```java
@SpringBootApplication
@EnableCORS
public class MySpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(MySpringBootApplication.class, args);
  }

}
```


# Contribution

Would you like to add something or improve source? Create new issue, let's discuss it 

- **If in doubt, please discuss your ideas first before providing a pull request. This often helps avoid a lot of unnecessary work. In particular, we might prefer not to prioritise a particular feature for another while.**
- Fork the repository.
- The commit message should reference the issue number.
- Check out and work on your own fork.
- Try to make your commits as atomic as possible. Related changes to three files should be committed in one commit.
- Try not to modify anything unrelated.

Source: https://github.com/jOOQ/jOOQ

# More

- Check out my [website](https://jpomykala.com)

# License
GNU General Public License v3.0
