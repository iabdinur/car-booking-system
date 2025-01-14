package com.iabdinur.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    boolean existsByCarRegNumberAndIsCanceledFalse(String regNumber);
    List<Booking> findByUserIdAndIsCanceledFalse(UUID userId);
    List<Booking> findByIsCanceledFalse();

}