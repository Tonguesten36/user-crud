package com.toshiba.intern.usercrud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(exclude = {io.sentry.spring.boot.jakarta.SentryAutoConfiguration.class})
@EnableJpaRepositories
public class UserCrudApplication {

    public static void main(String[] args)
    {

        SpringApplication.run(UserCrudApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        System.out.println("CommandLineRunner bean is being created.");
        return args -> System.out.println("Application has started!");
    }
}
