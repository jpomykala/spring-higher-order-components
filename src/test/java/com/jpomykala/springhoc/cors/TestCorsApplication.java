package com.jpomykala.springhoc.cors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = { "com.jpomykala.springhoc.cors" })
@RestController
public class TestCorsApplication extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
        SpringApplication.run(TestCorsApplication.class, args);
    }

    @GetMapping("/echo")
    public String echo()
    {
        return "hello";
    }
}
