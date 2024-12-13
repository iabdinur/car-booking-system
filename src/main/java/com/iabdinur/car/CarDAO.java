package com.iabdinur.car;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    private static final List<Car> cars;

    static {

        cars = new ArrayList<>();
        cars.add(new Car( "1234",
                    CarBrand.TESLA,
                    new BigDecimal("50000"),
                    true));
        cars.add(new Car("5678",
                    CarBrand.BML,
                    new BigDecimal("30000"),
                    false));
        cars.add(new Car("8765",
                    CarBrand.TOYOTA,
                    new BigDecimal("25000"),
                    true));
        cars.add(new Car("9123",
                    CarBrand.VW,
                    new BigDecimal("20000"),
                    false));
    };

    public List<Car> getAllCars(){
        return cars;
    }
}
