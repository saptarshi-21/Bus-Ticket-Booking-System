package com.cts.BusTicketBookingSystem.config;

import com.cts.BusTicketBookingSystem.model.Role;
import com.cts.BusTicketBookingSystem.model.Users;
import com.cts.BusTicketBookingSystem.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Autowired
    private UsersRepository usersRepository;

    @PostConstruct
    public void init() {
        List<Users> existingUsers = usersRepository.findAll();

        Users admin = new Users();
        admin.setName("Admin");
        admin.setEmail("abcd123@gmail.com");
        admin.setPassword("Abcd@123");
        admin.setPhoneNumber("1234567890");
        admin.setRole(Role.ADMINISTRATOR);

        Users busOperator1 = new Users();
        busOperator1.setName("Bus Operator 1");
        busOperator1.setEmail("busoperator1@example.com");
        busOperator1.setPassword("Operator@123");
        busOperator1.setPhoneNumber("0987654321");
        busOperator1.setRole(Role.BUS_OPERATOR);

        Users busOperator2 = new Users();
        busOperator2.setName("Bus Operator 2");
        busOperator2.setEmail("busoperator2@example.com");
        busOperator2.setPassword("Operator@123");
        busOperator2.setPhoneNumber("1122334455");
        busOperator2.setRole(Role.BUS_OPERATOR);

        List<Users> usersToSave = Arrays.asList(admin, busOperator1, busOperator2);

        for (Users user : usersToSave) {
            boolean emailExists = existingUsers.stream()
                    .anyMatch(existingUser -> existingUser.getEmail().equals(user.getEmail()));
            if (!emailExists) {
                usersRepository.save(user);
            }
        }
    }
}