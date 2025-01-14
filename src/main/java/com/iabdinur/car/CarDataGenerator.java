package com.iabdinur.car;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.Random;

@Component
public class CarDataGenerator implements CommandLineRunner {

    private static final String[] CAR_BRANDS = {
            "TESLA", "BMW", "VW", "TOYOTA", "MERCEDES", "FORD", "HONDA"
    };

    private final CarRepository carRepository;

    public CarDataGenerator(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Generate 25 fake cars
        for (int i = 0; i < 25; i++) {
            String regNumber = generateUKRegNumber(random);
            CarBrand manufacturer = CarBrand.values()[random.nextInt(CarBrand.values().length)];
            BigDecimal price = BigDecimal.valueOf(10000 + (50000 - 10000) * random.nextDouble());
            boolean isElectric = manufacturer == CarBrand.TESLA || random.nextBoolean();
            LocalDateTime createdAt = faker.date().past(365, TimeUnit.DAYS).toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();

            Car car = new Car(regNumber, manufacturer, price, isElectric, createdAt);

            // Save car to the database
            carRepository.save(car);
        }
        System.out.println("Successfully saved 25 cars to the database.");
    }

    // Method to generate a UK-style registration number (format: AA12 BBB)
    private String generateUKRegNumber(Random random) {
        String lettersPart1 = random.ints(2, 'A', 'Z' + 1)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        String digits = String.format("%02d", random.nextInt(100));
        String lettersPart2 = random.ints(3, 'A', 'Z' + 1)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return lettersPart1 + digits + " " + lettersPart2;
    }
}
