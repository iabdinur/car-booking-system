package com.iabdinur.booking;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class BookingListDataAccessService implements BookingDAO {
    private static final List<Booking> bookings = new ArrayList<>();


    @Override
    public List<Booking> getBookings() {
        return bookings;
    }

    @Override
    public void book(Booking booking) {
        bookings.add(booking);
    }

    @Override
    public void cancelBooking(UUID bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId) && !booking.isCanceled()) {
                booking.setCanceled(true);
                return;
            }
        }
        throw new IllegalStateException(String.format("No active booking found with ID %s.", bookingId));
    }

}
