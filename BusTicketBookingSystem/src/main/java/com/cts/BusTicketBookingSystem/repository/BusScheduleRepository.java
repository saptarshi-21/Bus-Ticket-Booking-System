package com.cts.BusTicketBookingSystem.repository;

import com.cts.BusTicketBookingSystem.model.BusSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusScheduleRepository extends JpaRepository<BusSchedule, Long> {
}