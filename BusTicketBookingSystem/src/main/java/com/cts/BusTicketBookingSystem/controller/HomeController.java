package com.cts.BusTicketBookingSystem.controller;

import com.cts.BusTicketBookingSystem.model.BusSchedule;
import com.cts.BusTicketBookingSystem.service.BusScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private BusScheduleService busScheduleService;

    @GetMapping("/")
    public String home(Model model) {
        List<String> routes = Arrays.asList(
            "Chennai to Bangalore",
            "Chennai to Hyderabad",
            "Chennai to Kochi"
        );
        List<BusSchedule> busSchedules = busScheduleService.getAllBusSchedules();
        model.addAttribute("routes", routes);
        model.addAttribute("busSchedules", busSchedules);
        return "home";
    }
    
    @GetMapping("/about")
	public String aboutUs() {
		return "about";
	}
 
	@GetMapping("/contact")
	public String contactUs() {
		return "contact";
	}
    
}