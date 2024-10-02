package com.example.questionapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

public class QuestionAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestionAppApplication.class, args);
    }

}
