package com.iabdinur.car;

import com.github.javafaker.Faker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

public class CarDataGenerator {
    private static final String[] CAR_BRANDS = {
            "TESLA", "BMW", "VW", "TOYOTA", "MERCEDES", "FORD", "HONDA"
    };

    public static void main(String[] args) {
        Faker faker = new Faker();
        Random random = new Random();
        String csvPath = Paths.get("src/main/resources/cars.csv").toString();  // Path to save cars.csv

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            // Generate 25 fake cars
            for (int i = 0; i < 25; i++) {
                String regNumber = generateUKRegNumber(random);
                String brand = CAR_BRANDS[random.nextInt(CAR_BRANDS.length)];  // Select a random brand from the array
                double value = 10000 + (50000 - 10000) * random.nextDouble();  // Random value between 10,000 and 50,000
                boolean isElectric = brand.equals("TESLA") || random.nextBoolean();  // Tesla is always electric

                // Write car data to CSV
                writer.write(String.format("%s,%s,%.2f,%b\n", regNumber, brand, value, isElectric));
            }

            System.out.println("Successfully generated cars.csv at: " + csvPath);
        } catch (IOException e) {
            System.err.println("Error writing to cars.csv: " + e.getMessage());
        }
    }

    // Method to generate a UK-style registration number (format: AA12 BBB)
    private static String generateUKRegNumber(Random random) {
        String lettersPart1 = random.ints(2, 'A', 'Z' + 1)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        String digits = String.format("%02d", random.nextInt(100));  // Two-digit year identifier
        String lettersPart2 = random.ints(3, 'A', 'Z' + 1)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return lettersPart1 + digits + " " + lettersPart2;
    }
}
