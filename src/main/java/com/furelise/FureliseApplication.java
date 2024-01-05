package com.furelise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
//@EnableScheduling
//@PropertySource(value = ("classpath:schedule.properties"))
public class FureliseApplication {

    public static void main(String[] args) {
        SpringApplication.run(FureliseApplication.class, args);
    }

}
