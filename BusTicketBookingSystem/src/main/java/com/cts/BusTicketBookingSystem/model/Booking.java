package com.cts.BusTicketBookingSystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_seq")
    @SequenceGenerator(name = "booking_seq", sequenceName = "booking_sequence", allocationSize = 1)
    private Long bookingId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bus_schedule_id")
    private BusSchedule busSchedule;
    
    private LocalDateTime bookingTime;
    private int seatNumber;
    private String status;
    
    // Getters and Setters
    public Long getBookingId() {
        return bookingId;
    }
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    public Users getUser() {
        return user;
    }
    public void setUser(Users user) {
        this.user = user;
    }
    public BusSchedule getBusSchedule() {
        return busSchedule;
    }
    public void setBusSchedule(BusSchedule busSchedule) {
        this.busSchedule = busSchedule;
    }
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
    public int getSeatNumber() {
        return seatNumber;
    }
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}