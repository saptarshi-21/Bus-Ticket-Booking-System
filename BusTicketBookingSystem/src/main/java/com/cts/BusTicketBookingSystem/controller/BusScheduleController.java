package com.cts.BusTicketBookingSystem.controller;

import com.cts.BusTicketBookingSystem.model.BusSchedule;
import com.cts.BusTicketBookingSystem.service.BusScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class BusScheduleController {

    @Autowired
    private BusScheduleService busScheduleService;

    @GetMapping("/bus-schedule")
    public String viewBusSchedule(Model model) {
        List<BusSchedule> schedules = busScheduleService.getAllBusSchedules();
        if (schedules == null || schedules.isEmpty()) {
            model.addAttribute("error", "No bus schedules found.");
        } else {
            model.addAttribute("busSchedules", schedules);
        }
        return "busSchedule";
    }

    @PostMapping("/bus-schedule/add")
    public String addOrUpdateBusSchedule(@RequestBody BusSchedule busSchedule) {
        busScheduleService.saveBusSchedule(busSchedule);
        return "redirect:/bus-schedule";
    }
}