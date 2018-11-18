package com.tiger.bb_nt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class BbNtApplication {

    public static void main(String[] args) {
        SpringApplication.run(BbNtApplication.class, args);
    }
}
