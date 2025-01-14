package com.iabdinur.user;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class UserDataGenerator implements CommandLineRunner {

    private final UserRepository userRepository;

    public UserDataGenerator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        // Generate 25 fake users
        for (int i = 0; i < 25; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String name = faker.name().firstName();
            String email = String.format("%s.%s@gmail.com", firstName, lastName);
            LocalDateTime localDateTime = faker.date().past(365, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
            User user = new User(name, email, localDateTime);

            // Save user to the database
            userRepository.save(user);
        }
        System.out.println("Successfully saved 25 users to the database.");
    }
}
