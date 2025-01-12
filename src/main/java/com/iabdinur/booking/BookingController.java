package com.iabdinur.booking;

import com.iabdinur.car.Car;
import com.iabdinur.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Endpoint to book a car
    @PostMapping("/book")
    public UUID bookCar(
            @RequestParam UUID userId,
            @RequestParam String userName,
            @RequestParam String regNumber) {
        User user = new User(userId, userName);
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
        List<Booking> bookings = bookingService.getAllBookings();
        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId) && !booking.isCanceled()) {
                booking.setCanceled(true);
                return String.format("Booking with ID %s has been canceled successfully.", bookingId);
            }
        }
        throw new IllegalStateException(String.format("No active booking found with ID %s.", bookingId));
    }
}
