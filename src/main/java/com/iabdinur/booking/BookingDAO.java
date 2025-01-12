package com.iabdinur.booking;

import java.util.List;
import java.util.UUID;

public interface BookingDAO {

   List<Booking> getBookings();

    void book(Booking booking);

    void cancelBooking(UUID bookingId);

}