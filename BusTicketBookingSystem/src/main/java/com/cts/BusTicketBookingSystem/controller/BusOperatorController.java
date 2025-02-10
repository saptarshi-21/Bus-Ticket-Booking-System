package com.cts.BusTicketBookingSystem.controller;

import com.cts.BusTicketBookingSystem.model.BusSchedule;
import com.cts.BusTicketBookingSystem.model.Role;
import com.cts.BusTicketBookingSystem.model.Users;
import com.cts.BusTicketBookingSystem.service.BusScheduleService;

import jakarta.servlet.http.HttpSession;

import com.cts.BusTicketBookingSystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BusOperatorController {

    @Autowired
    private BusScheduleService busScheduleService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/bus-operator/dashboard")
    public String userDashboard(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user != null && user.getRole() == Role.BUS_OPERATOR) {
            return "busOperatorDashboard";
        }
        return "redirect:/bus-operator/login";
    }
    
    @GetMapping("/bus-operator/view-bus-schedules")
    public String viewBusSchedule(Model model) {
        List<BusSchedule> schedules = busScheduleService.getAllBusSchedules();
        if (schedules == null || schedules.isEmpty()) {
            model.addAttribute("error", "No bus schedules found.");
        } else {
            model.addAttribute("busSchedules", schedules);
        }
        return "busSchedule";
    }

    @GetMapping("/bus-operator/manage-bus-schedules")
    public String manageBusSchedules(@RequestParam(required = false) Long scheduleId, Model model) {
        if (scheduleId != null) {
            Optional<BusSchedule> busSchedule = busScheduleService.getBusScheduleById(scheduleId);
            model.addAttribute("busSchedule", busSchedule.orElse(new BusSchedule()));
        } else {
            model.addAttribute("busSchedule", new BusSchedule());
        }
        model.addAttribute("busSchedules", busScheduleService.getAllBusSchedules());
        return "manageOperatorBusSchedules";
    }

    @PostMapping("/bus-operator/manage-bus-schedules/add")
    public String addBusSchedule(@ModelAttribute BusSchedule busSchedule) {
        busScheduleService.saveBusSchedule(busSchedule);
        return "redirect:/bus-operator/manage-bus-schedules";
    }

    @PostMapping("/bus-operator/manage-bus-schedules/update")
    public String updateBusSchedule(@ModelAttribute BusSchedule busSchedule) {
        if (busSchedule.getScheduleId() == null) {
            System.out.println("Schedule ID is missing, update won't work!");
            return "redirect:/bus-operator/manage-bus-schedules";
        }
        busScheduleService.updateBusSchedule(busSchedule.getScheduleId(), busSchedule);
        return "redirect:/bus-operator/manage-bus-schedules";
    }

    @PostMapping("/bus-operator/manage-bus-schedules/delete")
    public String deleteBusSchedule(@RequestParam Long scheduleId) {
        busScheduleService.deleteBusSchedule(scheduleId);
        return "redirect:/bus-operator/manage-bus-schedules";
    }

    @GetMapping("/bus-operator/view-bookings")
    public String viewBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "viewBookings";
    }
}