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
//        Dotenv dotenv = Dotenv.load();
//
//        String jwt_secret = dotenv.get("JWT_SECRET");
//        String postgres_password = dotenv.get("POSTGRES_PASSWORD");
//        String postgres_username = dotenv.get("POSTGRES_USERNAME");
//        String postgres_database = dotenv.get("POSTGRES_DB_NAME");
//
//        System.out.println("JWT_SECRET: " + jwt_secret + "\n POSTGRES_PASSWORD: " + postgres_password + "\n POSTGRES_USERNAME: " + postgres_username + "\n POSTGRES_DB_NAME: " + postgres_database);



        return args -> System.out.println("Application has started!");
    }
}
