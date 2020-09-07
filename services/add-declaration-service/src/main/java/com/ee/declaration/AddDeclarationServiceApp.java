package com.ee.declaration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class AddDeclarationServiceApp {

    @Value("${spring.jackson.time-zone}")
    private String timeZone;

    public static void main(String[] args) {
        SpringApplication.run(AddDeclarationServiceApp.class, args);
    }

    @PostConstruct
    public void setDefaultTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of(timeZone)));
    }

}
