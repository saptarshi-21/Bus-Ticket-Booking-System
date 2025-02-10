package com.cts.BusTicketBookingSystem.controller;

import com.cts.BusTicketBookingSystem.model.Role;
import com.cts.BusTicketBookingSystem.model.Users;
import com.cts.BusTicketBookingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Users user) {
        user.setRole(Role.PASSENGER);
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, HttpSession session) {
        List<Users> users = userService.getUserByEmail(email);
        if (users.size() == 1 && users.get(0).getPassword().equals(password)) {
            session.setAttribute("user", users.get(0));
            return "redirect:/dashboard";
        }
        return "login";
    }

    @GetMapping("/admin/login")
    public String showAdminLoginForm() {
        return "adminLogin";
    }

    @PostMapping("/admin/login")
    public String loginAdmin(@RequestParam String email, @RequestParam String password, HttpSession session) {
        List<Users> users = userService.getUserByEmail(email);
        if (users.size() == 1 && users.get(0).getPassword().equals(password) && users.get(0).getRole() == Role.ADMINISTRATOR) {
            session.setAttribute("user", users.get(0));
            return "redirect:/admin/dashboard";
        }
        return "adminLogin";
    }

    @GetMapping("/bus-operator/login")
    public String showBusOperatorLoginForm() {
        return "busOperatorLogin";
    }

    @PostMapping("/bus-operator/login")
    public String loginBusOperator(@RequestParam String email, @RequestParam String password, HttpSession session) {
        List<Users> users = userService.getUserByEmail(email);
        if (users.size() == 1 && users.get(0).getPassword().equals(password) && users.get(0).getRole() == Role.BUS_OPERATOR) {
            session.setAttribute("user", users.get(0));
            return "redirect:/bus-operator/dashboard";
        }
        return "busOperatorLogin";
    }

    @GetMapping("/dashboard")
    public String userDashboard(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user != null && user.getRole() == Role.PASSENGER) {
            return "dashboard";
        }
        return "redirect:/login";
    }
    
    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
    	session.invalidate();
    	return "redirect:/";
    }
}