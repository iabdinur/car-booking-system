package com.iabdinur.car;

import com.iabdinur.booking.Booking;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity(name = "Car")
@Table(
        name = "cars",
        uniqueConstraints = {
                @UniqueConstraint(name = "car_reg_number_unique", columnNames = "reg_number")
        }
)
public class Car {

    @Id
    @Column(
            name = "reg_number",
            nullable = false,
            updatable = false,
            columnDefinition = "TEXT"
    )
    private String regNumber;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "manufacturer",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private CarBrand manufacturer;

    @Column(
            name = "price",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal price;

    @Column(
            name = "is_electric",
            nullable = false
    )
    private boolean isElectric;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "car",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Booking> bookings;

    public Car() {
    }

    public Car(String regNumber, CarBrand manufacturer, BigDecimal price, boolean isElectric, LocalDateTime createdAt) {
        this.regNumber = regNumber;
        this.manufacturer = manufacturer;
        this.price = price;
        this.isElectric = isElectric;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return isElectric == car.isElectric &&
                Objects.equals(regNumber, car.regNumber) &&
                manufacturer == car.manufacturer &&
                Objects.equals(price, car.price);
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
                ", createdAt=" + createdAt +
                '}';
    }
}
