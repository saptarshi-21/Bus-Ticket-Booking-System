package com.cts.BusTicketBookingSystem.controller;

import com.cts.BusTicketBookingSystem.model.BusSchedule;
import com.cts.BusTicketBookingSystem.model.Role;
import com.cts.BusTicketBookingSystem.model.Users;
import com.cts.BusTicketBookingSystem.model.Booking;
import com.cts.BusTicketBookingSystem.service.BookingService;
import com.cts.BusTicketBookingSystem.service.BusScheduleService;
import com.cts.BusTicketBookingSystem.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private BusScheduleService busScheduleService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private BookingService bookingService;
    
    @GetMapping("/admin/dashboard")
    public String userDashboard(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user != null && user.getRole() == Role.ADMINISTRATOR) {
            return "adminDashboard";
        }
        return "redirect:/admin/login";
    }

    @GetMapping("/admin/manage-bus-schedules")
    public String manageBusSchedules(@RequestParam(required = false) Long scheduleId, Model model) {
        if (scheduleId != null) {
            Optional<BusSchedule> busSchedule = busScheduleService.getBusScheduleById(scheduleId);
            model.addAttribute("busSchedule", busSchedule.orElse(new BusSchedule()));
        } else {
            model.addAttribute("busSchedule", new BusSchedule());
        }
        model.addAttribute("busSchedules", busScheduleService.getAllBusSchedules());
        return "manageBusSchedules";
    }

    @PostMapping("/admin/manage-bus-schedules/add")
    public String addBusSchedule(@ModelAttribute BusSchedule busSchedule) {
        busScheduleService.saveBusSchedule(busSchedule);
        return "redirect:/admin/manage-bus-schedules";
    }

    @PostMapping("/admin/manage-bus-schedules/update")
    public String updateBusSchedule(@ModelAttribute BusSchedule busSchedule) {
        if (busSchedule.getScheduleId() == null) {
            return "redirect:/admin/manage-bus-schedules";
        }
        busScheduleService.updateBusSchedule(busSchedule.getScheduleId(), busSchedule);
        return "redirect:/admin/manage-bus-schedules";
    }

    @PostMapping("/admin/manage-bus-schedules/delete")
    public String deleteBusSchedule(@RequestParam Long scheduleId) {
        busScheduleService.deleteBusSchedule(scheduleId);
        return "redirect:/admin/manage-bus-schedules";
    }
    
    @GetMapping("/admin/manage-users")
    public String manageUsers(@RequestParam(required = false) Long userId, Model model) {
        if (userId != null) {
            Optional<Users> user = userService.getUserById(userId);
            model.addAttribute("user", user.orElse(new Users()));
        } else {
            model.addAttribute("user", new Users());
        }
        model.addAttribute("users", userService.getAllUsers());
        return "manageUsers";
    }
    
    @GetMapping("/admin/view-bookings")
    public String viewBookings(Model model) {
    	List<Booking> bookings = bookingService.getAllBookings();
    	model.addAttribute("bookings", bookings);
    	return "viewBookings";
    }
    
    @GetMapping("/admin/manage-bookings")
    public String manageBookings(Model model) {
    	List<Booking> bookings = bookingService.getAllBookings();
    	model.addAttribute("bookings", bookings);
    	return "manageBookings";
    }
    
    @PostMapping("/admin/manage-bookings")
    public String updateBookingStatus(@RequestParam Long bookingId, @RequestParam String status) {
        bookingService.updateBookingStatus(bookingId, status);
        return "redirect:/admin/manage-bookings";
    }
    
    @PostMapping("/admin/manage-bookings/confirm")
    public String confirmBooking(@RequestParam Long bookingId) {
        bookingService.confirmBooking(bookingId);
        return "redirect:/admin/manage-bookings";
    }
     
    @PostMapping("/admin/manage-bookings/cancel")
    public String cancelBooking(@RequestParam Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return "redirect:/admin/manage-bookings";
    }
    
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute Users user) {
    	userService.saveUser(user);
    	return "redirect:/admin/manage-users";
    }
    
    @RequestMapping("/editUser/{id}")
    public String editUser(@PathVariable("id") Long userId,Model model) {
    	Users user = userService.getUserById(userId).get();
    	model.addAttribute("user", user);
    	return "userEdit";
    }
    
    @RequestMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long userId) {
    	userService.deleteUser(userId);
    	return "redirect:/admin/manage-users";
    }    
}
