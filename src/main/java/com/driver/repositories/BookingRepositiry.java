package com.driver.repositories;

import com.driver.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BookingRepositiry extends JpaRepository<Booking,String> {

}
