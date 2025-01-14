package com.iabdinur.booking;

import com.iabdinur.car.Car;
import com.iabdinur.user.User;
import com.iabdinur.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    // Endpoint to book a car
    @PostMapping("/book")
    public UUID bookCar(
            @RequestParam UUID userId,
            @RequestParam String regNumber) {
        // Retrieve the user by ID from the database
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("No user found with ID: " + userId);
        }
        return bookingService.bookCar(user, regNumber);
    }

    // Endpoint to get all bookings
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // Endpoint to get all cars booked by a user
    @GetMapping("/user/{userId}")
    public List<Car> getUserBookedCars(@PathVariable UUID userId) {
        return bookingService.getUserBookedCars(userId);
    }

    // Endpoint to cancel a booking by booking ID
    @DeleteMapping("/{bookingId}")
    public String cancelBooking(@PathVariable UUID bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null || booking.isCanceled()) {
            throw new IllegalStateException(String.format("No active booking found with ID %s.", bookingId));
        }
        booking.setCanceled(true);
        bookingService.saveBooking(booking);  // Save the updated booking
        return String.format("Booking with ID %s has been canceled successfully.", bookingId);
    }
}
