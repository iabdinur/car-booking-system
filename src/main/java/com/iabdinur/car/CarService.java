package com.iabdinur.car;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCar(String regNumber) {
        return carRepository.findById(regNumber)
                .orElseThrow(() -> new IllegalStateException(String.format("Car with reg %s not found", regNumber)));
    }

    public List<Car> getAllElectricCars() {
        List<Car> cars = getAllCars();
        if (cars.isEmpty()) {
            return Collections.emptyList();
        }
        return cars.stream()
                .filter(Car::isElectric)
                .toList();
    }
}