package com.iabdinur.user;

import com.github.javafaker.Faker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

public class UserDataGenerator {
    public static void main(String[] args) {
        Faker faker = new Faker();
        String csvPath = Paths.get("src/main/resources/users.csv").toString();  // Path to save users.csv

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            // Generate 25 fake users
            for (int i = 0; i < 25; i++) {
                String uuid = UUID.randomUUID().toString();
                String name = faker.name().firstName();


                // Write user data to CSV
                writer.write(String.format("%s,%s\n", uuid, name));
            }

            System.out.println("Successfully generated users.csv at: " + csvPath);
        } catch (IOException e) {
            System.out.println("Error writing to user.csv: " + e.getMessage());
        }
    }
}
