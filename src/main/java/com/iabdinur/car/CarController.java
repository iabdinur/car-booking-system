package com.iabdinur.car;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{regNumber}")
    public Car getCar(@PathVariable String regNumber) {
        return carService.getCar(regNumber);
    }

    @GetMapping("/electric")
    public List<Car> getAllElectricCars() {
        return carService.getAllElectricCars();
    }
}

