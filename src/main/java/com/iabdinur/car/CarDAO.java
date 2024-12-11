package com.iabdinur.car;

import java.math.BigDecimal;

public class CarDAO {
    private static final  Car[] cars = {
            new Car( "1234",
                    CarBrand.TESLA,
                    new BigDecimal("50000"),
                    true),
            new Car("5678",
                    CarBrand.BML,
                    new BigDecimal("30000"),
                    false),
            new Car("8765",
                    CarBrand.TOYOTA,
                    new BigDecimal("25000"),
                    true),
            new Car("9123",
                    CarBrand.VW,
                    new BigDecimal("20000"),
                    false)
    };

    public Car[] getAllCars(){
        return cars;
    }
}
