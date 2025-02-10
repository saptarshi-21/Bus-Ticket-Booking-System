package com.cts.BusTicketBookingSystem.service;

import com.cts.BusTicketBookingSystem.model.Users;
import com.cts.BusTicketBookingSystem.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    public Users saveUser(Users user) {
        return usersRepository.save(user);
    }

    public Optional<Users> getUserById(Long userId) {
        return usersRepository.findById(userId);
    }

    public List<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }
    
    public Users updateUser(Long userId, Users user) {
    	user.setUserId(userId);
    	return usersRepository.save(user);
    }

    public void deleteUser(Long userId) {
    	usersRepository.deleteById(userId);
    }
    
}