package com.iabdinur.car;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Car {
    private String regNumber;
    private CarBrand manufacturer;
    private BigDecimal price;
    private boolean isElectric;

    public Car(String regNumber, CarBrand manufacturer, BigDecimal price, boolean isElectric) {
        this.regNumber = regNumber;
        this.manufacturer = manufacturer;
        this.price = price;
        this.isElectric = isElectric;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public CarBrand getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(CarBrand manufacturer) {
        this.manufacturer = manufacturer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isElectric() {
        return isElectric;
    }

    public void setElectric(boolean electric) {
        isElectric = electric;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return isElectric == car.isElectric && Objects.equals(regNumber, car.regNumber) && manufacturer == car.manufacturer && Objects.equals(price, car.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regNumber, manufacturer, price, isElectric);
    }

    @Override
    public String toString() {
        return "Car{" +
                "regNumber='" + regNumber + '\'' +
                ", manufacturer=" + manufacturer +
                ", price=" + price +
                ", isElectric=" + isElectric +
                '}';
    }


}
