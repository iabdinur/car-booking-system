package com.iabdinur.booking;

import com.iabdinur.car.Car;
import com.iabdinur.car.CarService;
import com.iabdinur.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class BookingService {
    private final BookingDAO bookingDAO;
    private final CarService carService;

    public BookingService(BookingDAO bookingDAO, CarService carService) {
        this.bookingDAO = bookingDAO;
        this.carService = carService;
    }

    public UUID bookCar(User user, String regNumber) {
        Car[] availableCars = getAvailableCars();
        if (availableCars.length == 0) {
            throw new IllegalStateException("No car available for renting");
        }
        for (Car availableCar : availableCars) {
            // let's make sure the car user wants still available
            if (availableCar.getRegNumber().equals(regNumber)) {
                Car car = carService.getCar(regNumber);
                UUID bookingId = UUID.randomUUID();
                BookingDAO.book(
                        new Booking(bookingId, user, car, LocalDateTime.now())
                );
                // at this point we are done therefore we can exit this method
                return bookingId;
            }
        }
        throw new IllegalStateException("Already booked. car with regNumber " + regNumber);
    }

    public static Car[] getUserBookedCars(UUID userId) {
        Booking[] bookings = BookingDAO.getBookings();
        int numberOfBookingsForUser = 0;

        for (Booking b : bookings) {
            if (b != null && b.getUser().getId().equals(userId)) {
                ++numberOfBookingsForUser;
            }
        }

        if (numberOfBookingsForUser == 0) {
            return new Car[0];
        }

        Car[] userCars = new Car[numberOfBookingsForUser];

        int index = 0;
        for (Booking booking : bookings) {
            if (booking != null && booking.getUser().getId().equals(userId)) {
                userCars[index++] = booking.getCar();
            }
        }
        return userCars;
    }

    public Car[] getAvailableCars() {
        return getCars(carService.getAllCars());
    }

    public Car[] getAvailableElectricCars() {
        return getCars(carService.getAllElectricCars());
    }

    private Car[] getCars(Car[] cars) {
        // no cars in the system yet
        if (cars.length == 0) {
            return new Car[0];
        }

        Booking[] bookings = BookingDAO.getBookings();

        // no bookings yet therefore all cars are available
        if (bookings.length == 0) {
            return cars;
        }

        // this variable is used to create new array for availableCars since we need the size
        int availableCarsCount = 0;

        for (Car car : cars) {
            // lets check if car part of any booking. if not then its available
            boolean booked = false;
            for (Booking booking : bookings) {
                if (booking == null || !booking.getCar().equals(car)) {
                    continue;
                }
                booked = true;
            }
            if (!booked) {
                ++availableCarsCount;
            }
        }

        Car[] availableCars = new Car[availableCarsCount];
        int index = 0;

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
                availableCars[index++] = car;
            }
        }

        return availableCars;
    }

    public Booking[] getBookings() {
        Booking[] bookings = BookingDAO.getBookings();

        int numberOfBookings = 0;

        for (Booking b : bookings) {
            if (b != null) {
                ++numberOfBookings;
            }
        }

        if (numberOfBookings == 0) {
            return new Booking[0];
        }

        Booking[] totalBookings = new Booking[numberOfBookings];

        int index = 0;
        for (Booking booking : bookings) {
            if (booking != null) {
                totalBookings[index++] = booking;
            }
        }
        return totalBookings;
    }
}

