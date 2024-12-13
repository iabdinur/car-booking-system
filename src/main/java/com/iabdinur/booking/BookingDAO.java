package com.iabdinur.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingDAO {
    private final static List<Booking> bookings;

    static {
        bookings = new ArrayList<Booking>();
    }

    public static List<Booking> getBookings() {
        return bookings;
    }

    public static void book(Booking booking) {
        bookings.add(booking);
    }

    public void cancelCarBooking(UUID id) {
    }

}