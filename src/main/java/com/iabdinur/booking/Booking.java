package com.iabdinur.booking;

import com.iabdinur.car.Car;
import com.iabdinur.user.User;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Booking {
    private UUID bookingId;
    private User user;
    private Car car;
    private LocalDateTime bookingTime;
    private boolean isCanceled;

    public Booking(UUID bookingId, User user, Car car, LocalDateTime bookingTime, boolean isCanceled) {
        this.bookingId = bookingId;
        this.user = user;
        this.car = car;
        this.bookingTime = bookingTime;
        this.isCanceled = isCanceled;
    }

    public Booking(UUID bookingId, User user, Car car, LocalDateTime bookingTime) {
        this(bookingId, user, car, bookingTime, false);
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return isCanceled == booking.isCanceled && Objects.equals(bookingId, booking.bookingId) && Objects.equals(user, booking.user) && Objects.equals(car, booking.car) && Objects.equals(bookingTime, booking.bookingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, user, car, bookingTime, isCanceled);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", user=" + user +
                ", car=" + car +
                ", bookingTime=" + bookingTime +
                ", isCanceled=" + isCanceled +
                '}';
    }
}
