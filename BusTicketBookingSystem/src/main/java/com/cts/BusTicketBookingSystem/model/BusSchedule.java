package com.cts.BusTicketBookingSystem.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "bus_schedule")
public class BusSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bus_schedule_seq")
    @SequenceGenerator(name = "bus_schedule_seq", sequenceName = "bus_schedule_seq", allocationSize = 1)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @Column(name = "bus_name", nullable = false)
    private String busName;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "departure_time", nullable = false)
    private String departureTime;

    @Column(name = "arrival_time", nullable = false)
    private String arrivalTime;

    @Column(name = "available_seats", nullable = false)
    private int availableSeats;

    // Relationship with Booking
    @OneToMany(mappedBy = "busSchedule", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Booking> bookings;

    // Constructors
    public BusSchedule() {
    }

    public BusSchedule(Long scheduleId, String busName, String source, String destination, String departureTime, String arrivalTime, int availableSeats) {
        this.scheduleId = scheduleId;
        this.busName = busName;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
    }

    // Getters and Setters
    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}