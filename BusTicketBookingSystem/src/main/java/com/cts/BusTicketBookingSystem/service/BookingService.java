package com.cts.BusTicketBookingSystem.service;

import com.cts.BusTicketBookingSystem.model.Booking;
import com.cts.BusTicketBookingSystem.model.BusSchedule;
import com.cts.BusTicketBookingSystem.repository.BookingRepository;
import com.cts.BusTicketBookingSystem.repository.BusScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BusScheduleRepository busScheduleRepository;

    public Booking saveBooking(Booking booking) {
        BusSchedule busSchedule = busScheduleRepository.findById(booking.getBusSchedule().getScheduleId())
                .orElseThrow(() -> new RuntimeException("Bus schedule not found."));
        booking.setBusSchedule(busSchedule);
        if (busSchedule.getAvailableSeats() > 0) {
            busSchedule.setAvailableSeats(busSchedule.getAvailableSeats() - 1);
            busScheduleRepository.save(busSchedule);
            return bookingRepository.save(booking);
        } else {
            throw new RuntimeException("No available seats for this bus schedule.");
        }
    }

    public Optional<Booking> getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public void confirmBooking(Long bookingId) {
        updateBookingStatus(bookingId, "CONFIRMED");
    }

    public void cancelBooking(Long bookingId) {
        updateBookingStatus(bookingId, "CANCELED");
    }
    
    public Booking updateBookingStatus(Long bookingId, String status) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            String currentStatus = booking.getStatus();
            booking.setStatus(status);
            bookingRepository.save(booking);
     
            if ("CONFIRMED".equalsIgnoreCase(status) && !"CONFIRMED".equalsIgnoreCase(currentStatus)) {
                adjustAvailableSeats(booking, -1);
            } else if ("CANCELED".equalsIgnoreCase(status) && "CONFIRMED".equalsIgnoreCase(currentStatus)) {
                adjustAvailableSeats(booking, 1);
            }
     
            return booking;
        }
        return null;
    }
     
    private void adjustAvailableSeats(Booking booking, int adjustment) {
        BusSchedule busSchedule = booking.getBusSchedule();
        if (busSchedule != null) {
            busSchedule.setAvailableSeats(busSchedule.getAvailableSeats() + adjustment);
            busScheduleRepository.save(busSchedule);
        }
      }
}