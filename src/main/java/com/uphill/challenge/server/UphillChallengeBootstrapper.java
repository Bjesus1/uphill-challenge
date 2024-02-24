package com.uphill.challenge.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Bootstrapper class.
 */
@SpringBootApplication
@ComponentScan("com")
public class UphillChallengeBootstrapper {

    public static void main(String[] args) {
        SpringApplication.run(UphillChallengeBootstrapper.class, args);
    }

}
