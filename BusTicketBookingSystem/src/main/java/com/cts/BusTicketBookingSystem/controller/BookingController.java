package com.cts.BusTicketBookingSystem.controller;

import com.cts.BusTicketBookingSystem.model.Booking;
import com.cts.BusTicketBookingSystem.model.BusSchedule;
import com.cts.BusTicketBookingSystem.model.Users;
import com.cts.BusTicketBookingSystem.service.BookingService;
import com.cts.BusTicketBookingSystem.service.BusScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BusScheduleService busScheduleService;

    @GetMapping("/book-ticket")
    public String showBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("busSchedules", busScheduleService.getAllBusSchedules());
        return "bookTicket";
    }

    @PostMapping("/book-ticket")
    public String bookTicket(@ModelAttribute Booking booking, HttpSession session,RedirectAttributes redirectAttributes) {
        Users user = (Users) session.getAttribute("user");
        if (user != null) {
            booking.setUser(user);
            booking.setBookingTime(LocalDateTime.now());
            Long id = booking.getBusSchedule().getScheduleId();
            
            BusSchedule busSchedule = busScheduleService.getBusScheduleById(id).get();
            
            if(busSchedule.getAvailableSeats() < booking.getSeatNumber()) {
            	redirectAttributes.addFlashAttribute("errorMessage", "Seat not available");
            	return "redirect:/book-ticket";
            }
            bookingService.saveBooking(booking);
            return "redirect:/view-bookings";
        }
        return "redirect:/login";
    }

    @GetMapping("/view-bookings")
    public String viewBookings(Model model, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user != null) {
            List<Booking> bookings = bookingService.getAllBookings();
            model.addAttribute("bookings", bookings);
            return "viewUserBookings";
        }
        return "redirect:/login";
    }
}