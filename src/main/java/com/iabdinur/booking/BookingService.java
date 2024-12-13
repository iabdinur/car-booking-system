package com.iabdinur.booking;

import com.iabdinur.car.Car;
import com.iabdinur.car.CarService;
import com.iabdinur.user.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BookingService {
    private final CarService carService;
    private final BookingDAO bookingDAO;

    public BookingService(BookingDAO bookingDAO, CarService carService) {
        this.bookingDAO = bookingDAO;
        this.carService = carService;
    }

    public UUID bookCar(User user, String regNumber) {
        List<Car> availableCars = getAvailableCars();
        if (availableCars.isEmpty()) {
            throw new IllegalStateException("No car available for renting");
        }
        for (Car availableCar : availableCars) {
            // let's make sure the car user wants still available
            if (availableCar.getRegNumber().equals(regNumber)) {
                Car car = carService.getCar(regNumber);
                UUID bookingId = UUID.randomUUID();
                bookingDAO.book(new Booking(bookingId, user, car, LocalDateTime.now()));
                // at this point we are done therefore we can exit this method
                return bookingId;
            }
        }
        throw new IllegalStateException("Already booked. car with regNumber " + regNumber);
    }

    public static List<Car> getUserBookedCars(UUID userId) {
        List<Booking> bookings = BookingDAO.getBookings();
        List<Car> userCars = new ArrayList<>();


        for (Booking booking : bookings) {
            if (booking != null && booking.getUser().getId().equals(userId)) {
                userCars.add(booking.getCar());

            }
        }
        return userCars;
    }

    public List<Car> getAvailableCars() {
        return getCars(carService.getAllCars());
    }

    public List<Car> getAvailableElectricCars() {
        return getCars(carService.getAllElectricCars());
    }

    private List<Car> getCars(List<Car>cars) {
        // no cars in the system yet
        if (cars.isEmpty()) {
            return Collections.emptyList();
        }

        List<Booking> bookings = BookingDAO.getBookings();

        // no bookings yet therefore all cars are available
        if (bookings.isEmpty()) {
            return cars;
        }

        List<Car> availableCars = new ArrayList<>();
        // populate available cars
        for (Car car : cars) {
            // lets check if car part of any booking.
            // if not then its available but this time we add it to available cars
            boolean booked = false;
            for (Booking booking : bookings) {
                if (booking == null || !booking.getCar().equals(car)) {
                    continue;
                }
                booked = true;
            }
            if (!booked) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }

    public List<Booking> getAllBookings() {
        return BookingDAO.getBookings();
    }
}

