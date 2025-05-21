package com.example.web4_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;

@EnableMBeanExport
@SpringBootApplication
public class Web4Application {
    private String to_show_diff_work = "jopa";
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL Driver not found!", e);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Web4Application.class, args);
    }

}
