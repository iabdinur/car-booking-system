package com.iabdinur.car;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Repository
public class CarFileDataAccessService implements CarDAO {

    @Override
    public List<Car> getAllCars() {
        File file = new File(getClass().getClassLoader().getResource("cars.csv").getPath());

        List<Car> cars = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            // Skip the header line
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Read each line and create Car objects
            while (scanner.hasNextLine()) {
                String[] split = scanner.nextLine().split(",");
                String regNumber = split[0];
                CarBrand brand = CarBrand.valueOf(split[1].toUpperCase());  // Convert string to CarBrand enum
                BigDecimal value = new BigDecimal(split[2]);  // Convert string to BigDecimal
                boolean isElectric = Boolean.parseBoolean(split[3]);

                cars.add(new Car(regNumber, brand, value, isElectric));
            }
            return cars;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read cars.csv", e);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid data format in cars.csv", e);
        }
    }
}
