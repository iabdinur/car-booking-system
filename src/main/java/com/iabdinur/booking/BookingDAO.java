package com.iabdinur.booking;

import java.util.UUID;

public class BookingDAO {
    private static final Booking[] bookings;

    static {
        bookings = new Booking[10];
    }

    public static Booking[] getBookings() {
        return bookings;
    }

    public static void book(Booking carBooking) {
        int nextFreeIndex = -1;

        for (int i = 0; i < bookings.length; i++) {
            if (bookings[i] == null) {
                nextFreeIndex = i;
            }
        }

        if (nextFreeIndex > -1) {
            bookings[nextFreeIndex] = carBooking;
            return;
        }

        // full array
        // copy all bookings to new array with bigger space
        Booking[] biggerCarBookings = new Booking[bookings.length + 10];

        for (int i = 0; i < bookings.length; i++) {
            biggerCarBookings[i] = bookings[i];
        }

        // finally add new booking
        biggerCarBookings[bookings.length] = carBooking;
    }
    public void cancelBooking(UUID id) {
    }
}