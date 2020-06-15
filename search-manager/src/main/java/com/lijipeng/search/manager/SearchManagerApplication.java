package com.lijipeng.search.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.lijipeng.search.manager"})
@ServletComponentScan
@SpringBootApplication
public class SearchManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchManagerApplication.class, args);
    }

}
