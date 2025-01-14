package com.iabdinur.booking;

import com.iabdinur.car.Car;
import com.iabdinur.car.CarService;
import com.iabdinur.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CarService carService;

    public BookingService(BookingRepository bookingRepository, CarService carService) {
        this.bookingRepository = bookingRepository;
        this.carService = carService;
    }

    public UUID bookCar(User user, String regNumber) {
        // Check if the car is already booked and not canceled
        boolean isCarBooked = bookingRepository.existsByCarRegNumberAndIsCanceledFalse(regNumber);
        if (isCarBooked) {
            throw new IllegalStateException("Car with regNumber " + regNumber + " is already booked.");
        }

        // Get the car details
        Car car = carService.getCar(regNumber);

        // Create and save a new booking with a generated UUID
        Booking booking = new Booking(user, car, LocalDateTime.now());
        bookingRepository.save(booking);

        return booking.getId();  // Return the generated bookingId
    }

    public List<Car> getUserBookedCars(UUID userId) {
        return bookingRepository.findByUserIdAndIsCanceledFalse(userId)
                .stream()
                .map(Booking::getCar)
                .collect(Collectors.toList());
    }

    public List<Car> getAvailableCars() {
        List<Car> allCars = carService.getAllCars();
        List<Car> bookedCars = bookingRepository.findByIsCanceledFalse()
                .stream()
                .map(Booking::getCar)
                .collect(Collectors.toList());

        return allCars.stream()
                .filter(car -> !bookedCars.contains(car))
                .collect(Collectors.toList());
    }

    public List<Car> getAvailableElectricCars() {
        List<Car> allElectricCars = carService.getAllElectricCars();
        List<Car> bookedCars = bookingRepository.findByIsCanceledFalse()
                .stream()
                .map(Booking::getCar)
                .collect(Collectors.toList());

        return allElectricCars.stream()
                .filter(car -> !bookedCars.contains(car))
                .collect(Collectors.toList());
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(UUID bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }

    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

}
