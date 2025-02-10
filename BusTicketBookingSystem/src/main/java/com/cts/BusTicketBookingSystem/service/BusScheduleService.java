package com.cts.BusTicketBookingSystem.service;

import com.cts.BusTicketBookingSystem.model.BusSchedule;
import com.cts.BusTicketBookingSystem.repository.BusScheduleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusScheduleService {

    @Autowired
    private BusScheduleRepository busScheduleRepository;

    public BusSchedule saveBusSchedule(BusSchedule busSchedule) {
        return busScheduleRepository.save(busSchedule);
    }

    public Optional<BusSchedule> getBusScheduleById(Long scheduleId) {
        return busScheduleRepository.findById(scheduleId);
    }

    public List<BusSchedule> getAllBusSchedules() {
        List<BusSchedule> schedules = busScheduleRepository.findAll();
        return (schedules != null) ? schedules : List.of();
    }

    public void deleteBusSchedule(Long scheduleId) {
        busScheduleRepository.deleteById(scheduleId);
    }
    
    public void updateBusSchedule(Long scheduleId, BusSchedule updatedSchedule) {
        BusSchedule existingSchedule = busScheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new RuntimeException("Bus Schedule not found with ID: " + scheduleId));
        
        existingSchedule.setBusName(updatedSchedule.getBusName());
        existingSchedule.setSource(updatedSchedule.getSource());
        existingSchedule.setDestination(updatedSchedule.getDestination());
        existingSchedule.setDepartureTime(updatedSchedule.getDepartureTime());
        existingSchedule.setArrivalTime(updatedSchedule.getArrivalTime());
        existingSchedule.setAvailableSeats(updatedSchedule.getAvailableSeats());

        busScheduleRepository.save(existingSchedule); 
    }
}